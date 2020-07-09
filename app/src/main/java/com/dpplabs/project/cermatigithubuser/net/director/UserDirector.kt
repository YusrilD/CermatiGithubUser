package com.dpplabs.project.cermatigithubuser.net.director

import com.dpplabs.project.cermatigithubuser.net.SystemNet
import com.dpplabs.project.cermatigithubuser.net.User.UserNet
import com.dpplabs.project.cermatigithubuser.net.github.RepositoryGithub
import com.dpplabs.project.cermatigithubuser.net.github.ResponseNet
import com.dpplabs.project.cermatigithubuser.net.github.ShortUserGithub
import com.dpplabs.project.cermatigithubuser.presentation.presenter.UserPresenter
import java.io.IOException

class UserDirector (private val system: SystemNet,
                    override val user: UserModel,
                    private val openInBrowser: (ShortUserGithub)->Unit,
                    private val onFavoriteChanged: (UserModel)->Unit,
                    private val openRepo: (RepositoryGithub)->Unit) : UserNet {

    override fun getUser(presenter: UserPresenter) {
        system.doOnBackground(
            {
                var callback: ()->Unit
                try {
                    val url = user.url
                    val result = String(system.httpGet(url, null).t)
                    val user = ResponseNet.parseUser(result)
                    callback = { presenter.onUserReceived(user) }
                } catch (e: IOException) {
                    e.printStackTrace()
                    callback = { presenter.showLoadingError() }
                } catch (e: ResponseNet.ParseException) {
                    e.printStackTrace()
                    callback = { presenter.showParseError() }
                } catch (e: Exception) {
                    e.printStackTrace()
                    callback = { presenter.showUnexpectedError() }
                }
                system.doOnForeground(callback)
            }
        )
    }

    override fun getRepositories(presenter: UserPresenter) {
        system.doOnBackground(
            {
                var callback: ()-> Unit
                try {
                    val url = user.repositories
                    val result = String(system.httpGet(url, null).t)
                    val repos = ResponseNet.parseRepositories(result)
                    callback = { presenter.onReposReceived(repos) }
                } catch (e: IOException) {
                    e.printStackTrace()
                    callback = { presenter.showLoadingError() }
                } catch (e: ResponseNet.ParseException) {
                    e.printStackTrace()
                    callback = { presenter.showParseError() }
                } catch (e: Exception) {
                    e.printStackTrace()
                    callback = { presenter.showUnexpectedError() }
                }

                system.doOnForeground(callback)
            }
        )
    }

    override fun getPinnedRepositories(presenter: UserPresenter) {
        //TODO getPinnedRepositories
    }

    override fun openInBrowser() {
        openInBrowser(user)
    }

    override fun notifyFavorChanged(userShort: UserModel) {
        onFavoriteChanged(user)
    }

    override fun onRepoClick(repository: RepositoryGithub) {
        openRepo(repository)
    }
}