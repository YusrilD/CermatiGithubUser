package com.dpplabs.project.cermatigithubuser.net

import com.dpplabs.project.cermatigithubuser.net.util.ParamHttp
import com.dpplabs.project.cermatigithubuser.net.util.Pair
import java.io.IOException
import java.net.URL

interface SystemNet {

    fun doOnBackground(background: ()->Unit)
    fun doOnForeground(foreground: ()->Unit)
    @Throws(IOException::class)
    fun httpGet(url: URL, params: ParamHttp?): Pair<ByteArray, ParamHttp>

    fun getSavedStringArray(title: String, def: Array<String>?): Array<String>
    fun saveStringArray(title: String, array: Array<String>)

    fun removeSaved(str: String)

}