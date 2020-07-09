package com.dpplabs.project.cermatigithubuser.presentation.view

import com.dpplabs.project.cermatigithubuser.net.director.UserModel
import com.dpplabs.project.cermatigithubuser.net.github.UserGithub

interface UserViewInterface {

    fun init()
    fun showWithAnimation()
    fun setUser(param: UserGithub)
    fun setUser(userShort: UserModel)
    fun notifyReposUpdate(offset: Int, count: Int)
    fun closeView()
    fun showLoadingError()
    fun showParseError()
    fun showUnexpectedError()
    fun showUserLoading()
    fun hideUserLoading()
    fun showRepoLoading()
    fun hideRepoLoading()

}