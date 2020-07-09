package com.dpplabs.project.cermatigithubuser.net.director

import com.dpplabs.project.cermatigithubuser.net.github.ShortUserGithub

class UserModel : ShortUserGithub {

    fun isFavorite(): Boolean {
        return isFavorite
    }

    private var isFavorite = false

    constructor(id: Int, login: String) : super(id, login)

    constructor(id: Int, login: String, favorite: Boolean) : super(id, login) {
        isFavorite = favorite
    }

    constructor(user: ShortUserGithub, favorite: Boolean) : super(user.id, user.login) {
        this.isFavorite = favorite
    }

    fun setFavorite(favor: Boolean): UserModel {
        isFavorite = favor
        return this
    }
}
