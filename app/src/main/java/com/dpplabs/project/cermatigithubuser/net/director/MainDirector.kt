package com.dpplabs.project.cermatigithubuser.net.director

import com.dpplabs.project.cermatigithubuser.net.SystemNet
import com.dpplabs.project.cermatigithubuser.net.User.UserListNet
import com.dpplabs.project.cermatigithubuser.net.github.RepositoryGithub
import com.dpplabs.project.cermatigithubuser.net.github.ShortUserGithub
import com.dpplabs.project.cermatigithubuser.presentation.presenter.CoordinatorPresenter

class MainDirector(

    private val coordinator: CoordinatorPresenter,
    private val system: SystemNet
) {
    private var userListInteractor: UserListDirector?= null
    private val favorites: FavoriteManagement
    private var mode = MODE_SEARCH

    init {
        favorites = FavoriteManagement(system)
    }

    fun onSearchInput(text: String) {
        mode = MODE_SEARCH
        initUserListInteractor()
        val text = text.trim { it <= ' ' }
        if (text == "") return
        userListInteractor!!.setObservable(SearchObserv(text, system, favorites))
    }

    fun onFavoritesOpen() {
        mode = MODE_FAVOR
        initUserListInteractor()
        userListInteractor!!.setObservable(FavoriteObserv(favorites))
    }

    val userListUseCase: UserListNet?
        get() {
            initUserListInteractor()
            return userListInteractor
        }

    private val onFavoriteChanged = { user: UserModel ->
        if (user.isFavorite())
            favorites.addToFavorites(user)
        else
            favorites.removeFromFavorites(user)
        if (mode == MODE_FAVOR)
            onFavoritesOpen()
        else if (mode == MODE_SEARCH) userListInteractor!!.updateUser(user)
    }

    private fun initUserListInteractor() {
        if (userListInteractor == null)
            userListInteractor = UserListDirector(
                system,
                { userModel: UserModel ->
                    coordinator.onOpenUser(
                        UserDirector(
                            system,
                            userModel,
                            { user: ShortUserGithub -> coordinator.openInBrowser(user.htmlUrl) },
                            onFavoriteChanged,
                            { repo: RepositoryGithub -> coordinator.openInBrowser(repo.htmlUrl) })
                    )
                },
                onFavoriteChanged
            )
    }

    companion object {

        private val MODE_SEARCH = 1
        private val MODE_FAVOR = 2
    }
}
