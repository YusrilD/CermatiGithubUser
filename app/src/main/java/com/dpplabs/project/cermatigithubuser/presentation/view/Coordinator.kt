package com.dpplabs.project.cermatigithubuser.presentation.view

import com.dpplabs.project.cermatigithubuser.presentation.presenter.UserListPresenter
import com.dpplabs.project.cermatigithubuser.presentation.presenter.UserPresenter
import java.net.URL

interface Coordinator {

    fun init()
    fun openSearchView()
    fun openUserList(presenter: UserListPresenter)
    fun openUser(presenter: UserPresenter)
    fun openBrowser(url: URL)
    fun closeView()
    fun closeUser()
    val isUserOpened: Boolean
}