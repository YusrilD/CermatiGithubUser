package com.dpplabs.project.cermatigithubuser.presentation.presenter

import com.dpplabs.project.cermatigithubuser.net.User.UserListNet
import com.dpplabs.project.cermatigithubuser.net.director.UserModel
import com.dpplabs.project.cermatigithubuser.presentation.view.UserList
import java.util.ArrayList

class UserListPresenter(private val interactor: UserListNet) {

    private val limit = 30
    private var offset: Int = 0
    private val padding = 6
    private var totalCount = 0

    var users: ArrayList<UserModel>? = null
        private set

    fun setView(view: UserList) {
        this.view = view
    }

    private var view: UserList? = null

    fun start() {
        view!!.init()
        offset = 0
        totalCount = 0
        users = ArrayList<UserModel>()
        interactor.subscribe(this)
    }

    fun addUsers(users: ArrayList<UserModel>, totalCount: Int) {
        this.users!!.addAll(users)
        val prevOffset = offset
        offset += users.size
        view!!.notifyUsersAdded(prevOffset, users.size)
        this.totalCount = totalCount
        if (this.users!!.size >= totalCount) view!!.hideLoading()
    }

    fun scrolled(visibleItemCount: Int, totalItemCount: Int, pastVisibleItems: Int) {
        if (visibleItemCount + pastVisibleItems + padding >= totalItemCount && users!!.size < totalCount) {
            interactor.updateList(limit, offset)
        }
    }

    fun showUnexpectedError() {
        view!!.showUnexpectedError()
    }

    fun clearList() {
        offset = 0
        totalCount = 0
        view!!.clearList()
        users!!.clear()
    }

    fun onUserClicked(user: UserModel) {
        interactor.openUser(user)
    }

    fun showLoadingError() {
        view!!.showLoadingError()
    }

    fun showParseError() {
        view!!.showParseError()
    }

    fun onBackPressed() {
        view!!.closeView()
    }

    fun getUserAt(i: Int): UserModel {
        return users!![i]
    }

    val userCount: Int
        get() = users!!.size

    fun onFavorIconClick(user: UserModel) {
        interactor.pushFavorite(user)
    }

    fun updateList() {
        view!!.showLoading()
        interactor.updateList(limit, offset)
    }

    fun updateUser(userModel: UserModel) {
        for (i in users!!.indices) {
            if (users!![i].id == userModel.id) {
                users!!.removeAt(i)
                users!!.add(i, userModel)
                view!!.notifyUserChanged(i)
            }
        }
    }
}
