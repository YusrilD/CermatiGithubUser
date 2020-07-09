package com.dpplabs.project.cermatigithubuser.net.director

import com.dpplabs.project.cermatigithubuser.net.github.ResponseNet
import java.io.IOException
import java.util.ArrayList
import com.dpplabs.project.cermatigithubuser.net.util.Pair

abstract class UserListObserv(var query: String) {

    @Throws(IOException::class, ResponseNet.ParseException::class)
    abstract fun obtain(limit: Int, offset: Int): Pair<ArrayList<UserModel>, Int>

    override fun equals(other: Any?): Boolean {
        if (other !is UserListObserv) return false
        val compareWith = other
        return this.query == compareWith.query
    }
}