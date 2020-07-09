package com.dpplabs.project.cermatigithubuser.net.director

import com.dpplabs.project.cermatigithubuser.net.github.ResponseNet
import java.io.IOException
import java.util.ArrayList
import com.dpplabs.project.cermatigithubuser.net.util.Pair

class FavoriteObserv(private val favorites: FavoriteManagement) : UserListObserv(FavoriteManagement.FAVORITES) {

    @Throws(IOException::class, ResponseNet.ParseException::class)
    override fun obtain(limit: Int, offset: Int): Pair<ArrayList<UserModel>, Int> {
        return Pair(favorites.getFavorites(), favorites.getFavorites().size)
    }
}