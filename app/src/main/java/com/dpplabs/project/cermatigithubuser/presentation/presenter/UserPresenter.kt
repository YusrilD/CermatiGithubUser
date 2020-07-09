package com.dpplabs.project.cermatigithubuser.presentation.presenter

import com.dpplabs.project.cermatigithubuser.net.User.UserNet
import com.dpplabs.project.cermatigithubuser.net.director.UserModel
import com.dpplabs.project.cermatigithubuser.net.github.RepositoryGithub
import com.dpplabs.project.cermatigithubuser.net.github.UserGithub
import com.dpplabs.project.cermatigithubuser.presentation.view.UserViewInterface
import com.dpplabs.project.cermatigithubuser.presentation.view.view.UserView
import java.util.ArrayList

class UserPresenter(private val interactor: UserNet) {
    private var view: UserView? = null

    private var user: UserGithub? = null
    private var repos: ArrayList<RepositoryGithub>? = null
    private var pinnedRepos: ArrayList<RepositoryGithub>? = null

    fun setView(view: UserView) {
        this.view = view
    }

    fun start() {
        view!!.init()
        updateUser()
    }

    private fun updateUser() {
        val user = interactor.user
        repos = ArrayList<RepositoryGithub>()
        view!!.setUser(user)
        interactor.getUser(this)
        interactor.getRepositories(this)
        //interactor.getPinnedRepositories(this);

    }

    fun onUserReceived(user: UserGithub) {
        this.user = user
        view!!.setUser(user)
        view!!.hideUserLoading()
    }

    fun onReposReceived(repos: ArrayList<RepositoryGithub>) {
        this.repos!!.addAll(repos)
        view!!.hideRepoLoading()
        if (pinnedRepos == null)
            view!!.notifyReposUpdate(0, this.repos!!.size)
        else
            updateReposWithPinned()
    }

    fun onPinnedReposReceived(repos: ArrayList<RepositoryGithub>) {
        pinnedRepos = repos
        updateReposWithPinned()
    }

    private fun updateReposWithPinned() {
        if (repos == null || pinnedRepos == null) return
        for (repo in repos!!) {
            for (pinRepo in pinnedRepos!!) {
                if (repo.id == pinRepo.id) {
                    repos!!.remove(repo)
                    repos!!.add(0, repo)
                }
            }
        }
        view!!.notifyReposUpdate(0, repos!!.size)
    }

    fun openInBrowser() {
        interactor.openInBrowser()
    }

    fun onBackPressed() {
        view!!.closeView()
    }

    fun showLoadingError() {
        view!!.showLoadingError()
    }

    fun showParseError() {
        view!!.showParseError()
    }

    fun showUnexpectedError() {
        view!!.showUnexpectedError()
    }

    fun onFavorClick(userShort: UserModel) {
        userShort.setFavorite(!userShort.isFavorite())
        interactor.notifyFavorChanged(userShort)
    }

    fun getRepositoryAt(i: Int): RepositoryGithub {
        return repos!![i]
    }

    fun onRepoClick(repository: RepositoryGithub) {
        interactor.onRepoClick(repository)
    }

    val reposCount: Int
        get() = repos!!.size
}
