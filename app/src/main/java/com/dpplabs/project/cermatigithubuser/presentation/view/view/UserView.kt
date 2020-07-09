package com.dpplabs.project.cermatigithubuser.presentation.view.view

import android.graphics.Bitmap
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dpplabs.project.cermatigithubuser.R
import com.dpplabs.project.cermatigithubuser.net.director.UserModel
import com.dpplabs.project.cermatigithubuser.net.github.RepositoryGithub
import com.dpplabs.project.cermatigithubuser.net.github.UserGithub
import com.dpplabs.project.cermatigithubuser.presentation.presenter.UserPresenter
import com.dpplabs.project.cermatigithubuser.presentation.util.View
import com.dpplabs.project.cermatigithubuser.presentation.util.ViewContainer
import com.dpplabs.project.cermatigithubuser.presentation.view.otherview.UserAdapter
import com.dpplabs.project.cermatigithubuser.presentation.view.otherview.ViewTools
import com.dpplabs.project.cermatigithubuser.presentation.view.viewholder.UserViewHolder
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter
import java.text.DateFormat
import com.dpplabs.project.cermatigithubuser.presentation.view.UserViewInterface

import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition

class UserView (container: ViewContainer, private val presenter: UserPresenter) : View(container), UserViewInterface {

    private val viewHolder: UserViewHolder
    private var adapter: UserAdapter? = null

    init {
        viewHolder = UserViewHolder(parent)
        presenter.setView(this)
    }

    override fun getView(): ViewGroup {
        return viewHolder.main
    }

    override fun start() {
        presenter.start()
    }

    override fun init() {
        viewHolder.back.setOnClickListener { presenter.onBackPressed() }
        adapter = UserAdapter(this)
        val mLayoutManager = LinearLayoutManager(context)
        viewHolder.content.layoutManager = mLayoutManager
        viewHolder.content.adapter = ScaleInAnimationAdapter(adapter)
    }

    override fun showWithAnimation() {

    }

    override fun setUser(param: UserGithub) {
        viewHolder.collapsingToolbarLayout.title = param.name

        viewHolder.userInfo.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        adapter!!.userPage.addView(viewHolder.userInfo, 0)

        viewHolder.login.text = param.login

        if (param.bio != "")
            viewHolder.bio.text = param.bio
        else
            viewHolder.bioLayout.visibility = android.view.View.GONE

        if (param.email != "")
            viewHolder.email.text = param.email
        else
            viewHolder.emailLayout.visibility = android.view.View.GONE

        if (param.company != "")
            viewHolder.company.text = param.company
        else
            viewHolder.companyLayout.visibility = android.view.View.GONE

        if (param.location != "")
            viewHolder.location.text = param.location
        else
            viewHolder.locationLayout.visibility = android.view.View.GONE

        /*viewHolder.createdTime.text = context.getString(R.string.created_at, DateFormat.getDateInstance().format(param.createdAt))
        viewHolder.updatedTime.text = context.getString(R.string.updated_at, DateFormat.getDateInstance().format(param.updatedAt))*/
    }

    override fun setUser(userShort: UserModel) {
        viewHolder.openInBrowser.setOnClickListener { presenter.openInBrowser() }
        val width = context.resources.displayMetrics.widthPixels
        val height = ViewTools.dpToPx(320, context)
        Glide
            .with(context)
            .asBitmap()
            .load(userShort.avatar.toString())
            .apply( RequestOptions().centerCrop())
            .into(object : SimpleTarget<Bitmap>(width, height) {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    viewHolder.header.setImageBitmap(resource)
                }

                /*override fun onResourceReady(resource: Bitmap?, glideAnimation: GlideAnimation<in Bitmap>?) {
                    viewHolder.header.setImageBitmap(resource)
                }*/
            })
        viewHolder.collapsingToolbarLayout.title = userShort.login
        viewHolder.favorite.setOnClickListener {
            presenter.onFavorClick(userShort)
            if (userShort.isFavorite())
                viewHolder.favorite.background = ContextCompat.getDrawable(context, R.drawable.menu_is_favorite)
            else
                viewHolder.favorite.background = ContextCompat.getDrawable(context, R.drawable.menu_favorite)
        }
        if (userShort.isFavorite())
            viewHolder.favorite.background = ContextCompat.getDrawable(context, R.drawable.menu_is_favorite)
        else
            viewHolder.favorite.background = ContextCompat.getDrawable(context, R.drawable.menu_favorite)
    }

    override fun notifyReposUpdate(offset: Int, count: Int) {
        adapter!!.notifyItemsAdded(offset, count)
    }

    override fun closeView() {
        closeSelf()
    }

    override fun showLoadingError() {
        Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
    }

    override fun showParseError() {
        Toast.makeText(context, "Error", Toast.LENGTH_LONG).show()
    }

    override fun showUnexpectedError() {
        Toast.makeText(context, "Error", Toast.LENGTH_LONG).show()
    }

    override fun showUserLoading() {
        adapter!!.userLoading.visibility = android.view.View.VISIBLE
    }

    override fun hideUserLoading() {
        adapter!!.userLoading.visibility = android.view.View.GONE
    }

    override fun showRepoLoading() {
        adapter!!.repositoriesLoading.visibility = android.view.View.VISIBLE
    }

    override fun hideRepoLoading() {
        adapter!!.repositoriesLoading.visibility = android.view.View.INVISIBLE
    }

    fun getRepositoryAt(i: Int): RepositoryGithub {
        return presenter.getRepositoryAt(i)
    }

    fun onRepoClick(repository: RepositoryGithub) {
        presenter.onRepoClick(repository)
    }

    val reposCount: Int
        get() = presenter.reposCount
}