package com.dpplabs.project.cermatigithubuser.presentation.presenter

import com.dpplabs.project.cermatigithubuser.net.SystemNet
import com.dpplabs.project.cermatigithubuser.net.User.UserNet
import com.dpplabs.project.cermatigithubuser.net.director.MainDirector
import com.dpplabs.project.cermatigithubuser.presentation.view.Coordinator
import java.net.URL

class CoordinatorPresenter(system: SystemNet) {

    private val interactor: MainDirector
    private var view: Coordinator? = null

    init {
        interactor = MainDirector(this, system)
    }

    fun setView(view: Coordinator) {
        this.view = view
    }

    fun start() {
        view!!.init()
    }

    fun onSearchInput(text: String) {
        interactor.onSearchInput(text)
    }

    fun onStartButton() {
        view!!.openSearchView()
        view!!.openUserList(UserListPresenter(interactor.userListUseCase!!))
    }

    fun onOpenUser(useCase: UserNet) {
        view!!.openUser(UserPresenter(useCase))
    }

    fun openInBrowser(url: URL) {
        view!!.openBrowser(url)
    }

    fun onFavoritesMenuClick() {
        interactor.onFavoritesOpen()
    }

    fun onBackPressed() {
        if (view!!.isUserOpened)
            view!!.closeUser()
        else
            view!!.closeView()
    }
}
