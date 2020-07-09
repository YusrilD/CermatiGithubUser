package com.dpplabs.project.cermatigithubuser.net.director

import com.dpplabs.project.cermatigithubuser.net.SystemNet
import com.dpplabs.project.cermatigithubuser.net.github.RequestNet
import com.dpplabs.project.cermatigithubuser.net.github.ResponseNet
import java.io.IOException
import java.util.*
import com.dpplabs.project.cermatigithubuser.net.util.Pair

class SearchObserv(
    query: String,
    private val system: SystemNet,
    private val favorites: FavoriteManagement
) : UserListObserv(query) {

    @Throws(IOException::class, ResponseNet.ParseException::class)
    override fun obtain(limit: Int, offset: Int): Pair<ArrayList<UserModel>, Int> {
        val url = RequestNet.searchUser(query, limit, offset)
        val result = String(system.httpGet(url!!, null).t)
        val users = ResponseNet.parseSearchResults(result)
        return Pair(
            favorites.sortFavorites(users.t),
            if (users.u > 1000) 1000 else users.u
        )
    }
}
