package com.dpplabs.project.cermatigithubuser.net.User

import com.dpplabs.project.cermatigithubuser.net.director.UserModel
import com.dpplabs.project.cermatigithubuser.net.github.RepositoryGithub
import com.dpplabs.project.cermatigithubuser.presentation.presenter.UserPresenter

interface UserNet {

    fun getUser(presenter: UserPresenter)
    val user: UserModel
    fun getRepositories(presenter: UserPresenter)
    fun getPinnedRepositories(presenter: UserPresenter)
    fun openInBrowser()
    fun notifyFavorChanged(userShort: UserModel)
    fun onRepoClick(repository: RepositoryGithub)

}