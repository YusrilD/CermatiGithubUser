package com.dpplabs.project.cermatigithubuser.presentation.view.viewholder

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.dpplabs.project.cermatigithubuser.R

class UserListViewHolder(parent: ViewGroup) {

    var recyclerView: RecyclerView

    init {
        recyclerView = LayoutInflater.from(parent.context).inflate(R.layout.user_list_layout, parent, false) as RecyclerView
    }

}