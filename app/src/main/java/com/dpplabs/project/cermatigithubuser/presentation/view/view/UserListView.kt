package com.dpplabs.project.cermatigithubuser.presentation.view.view

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import android.widget.Toast
import com.dpplabs.project.cermatigithubuser.R
import com.dpplabs.project.cermatigithubuser.net.director.UserModel
import com.dpplabs.project.cermatigithubuser.presentation.presenter.UserListPresenter
import com.dpplabs.project.cermatigithubuser.presentation.util.View
import com.dpplabs.project.cermatigithubuser.presentation.util.ViewContainer
import com.dpplabs.project.cermatigithubuser.presentation.view.UserList
import com.dpplabs.project.cermatigithubuser.presentation.view.otherview.UserListAdapter
import com.dpplabs.project.cermatigithubuser.presentation.view.viewholder.UserListViewHolder
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter
import jp.wasabeef.recyclerview.animators.LandingAnimator

class UserListView(container: ViewContainer, private val presenter: UserListPresenter) : View(container),
    UserList {
    private val viewHolder: UserListViewHolder
    private var adapter: UserListAdapter? = null

    init {
        viewHolder = UserListViewHolder(parent)
        presenter.setView(this)
    }

    override fun getView(): ViewGroup {
        return viewHolder.recyclerView
    }

    override fun start() {
        presenter.start()
    }

    override fun init() {
        viewHolder.recyclerView.itemAnimator = LandingAnimator()
        val mLayoutManager = LinearLayoutManager(context)
        viewHolder.recyclerView.layoutManager = mLayoutManager
        viewHolder.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) { //check for scroll down
                    val visibleItemCount = mLayoutManager.childCount
                    val totalItemCount = mLayoutManager.itemCount
                    val pastVisibleItems = mLayoutManager.findFirstVisibleItemPosition()
                    presenter.scrolled(visibleItemCount, totalItemCount, pastVisibleItems)
                }
            }
        })
        adapter = UserListAdapter(this)
        viewHolder.recyclerView.adapter = ScaleInAnimationAdapter(adapter)
    }

    override fun notifyUsersAdded(offset: Int, count: Int) {
        adapter!!.notifyItemsAdded(offset, count)
    }

    override fun clearList() {
        adapter!!.clearList()
    }

    override fun showLoadingError() {
        Toast.makeText(context, "Error Loading", Toast.LENGTH_SHORT).show()
    }

    override fun showParseError() {
        Toast.makeText(context, "Parse Error", Toast.LENGTH_LONG).show()
    }

    override fun showUnexpectedError() {
        Toast.makeText(context, "Unexpected Error", Toast.LENGTH_LONG).show()
    }

    override fun closeView() {

    }

    override fun hideLoading() {
        adapter!!.hideLoading()
    }

    override fun showLoading() {
        adapter!!.showLoading()
    }

    override fun notifyUserChanged(i: Int) {
        adapter!!.notifyNormalItemChanged(i)
    }

    fun getUserAt(i: Int): UserModel {
        return presenter.getUserAt(i)
    }

    val userCount: Int
        get() = presenter.userCount

    fun onFavorIconClick(user: UserModel) {
        presenter.onFavorIconClick(user)
    }

    fun onUserClicked(user: UserModel) {
        presenter.onUserClicked(user)
    }
}
