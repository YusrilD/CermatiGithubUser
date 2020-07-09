package com.dpplabs.project.cermatigithubuser.net.github

import java.net.MalformedURLException
import java.net.URL
import java.util.*

class RepositoryGithub (
    val id: Int,
    val name: String,
    val owner: ShortUserGithub,
    val isPrivateRepo: Boolean,
    val isFork: Boolean,
    val description: String,
    val language: String,
    val forks: Int,
    val createdAt: Date,
    val updatedAt: Date,
    val stars: Int) {

        val url: URL
        get() {
            try {
                return URL("https://api.github.com/repos/" + owner.login + "/" + name)
            } catch (e: MalformedURLException) {
                e.printStackTrace()
            }
            return null!!
        }

        val htmlUrl: URL
        get() {
            try {
                return URL("https://github.com/" + owner.login + "/" + name)
            } catch (e: MalformedURLException) {
                e.printStackTrace()
            }
            return null!!
        }

    }
