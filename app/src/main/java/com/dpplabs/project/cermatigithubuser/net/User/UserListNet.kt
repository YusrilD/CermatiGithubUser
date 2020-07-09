package com.dpplabs.project.cermatigithubuser.net.User

import com.dpplabs.project.cermatigithubuser.net.director.UserModel
import com.dpplabs.project.cermatigithubuser.presentation.presenter.UserListPresenter

interface UserListNet {

    fun subscribe(presenter: UserListPresenter)
    fun updateList(limit: Int, offset: Int)
    fun openUser(user: UserModel)
    fun pushFavorite(user: UserModel)

}