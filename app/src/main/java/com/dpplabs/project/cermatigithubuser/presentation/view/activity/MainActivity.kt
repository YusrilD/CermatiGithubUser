package com.dpplabs.project.cermatigithubuser.presentation.view.activity

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import com.arlib.floatingsearchview.FloatingSearchView
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion
import com.dpplabs.project.cermatigithubuser.R
import com.dpplabs.project.cermatigithubuser.presentation.AndroidInterface
import com.dpplabs.project.cermatigithubuser.presentation.presenter.CoordinatorPresenter
import com.dpplabs.project.cermatigithubuser.presentation.presenter.UserListPresenter
import com.dpplabs.project.cermatigithubuser.presentation.presenter.UserPresenter
import com.dpplabs.project.cermatigithubuser.presentation.util.ViewContainer
import com.dpplabs.project.cermatigithubuser.presentation.view.Coordinator
import com.dpplabs.project.cermatigithubuser.presentation.view.otherview.AnimationManager
import com.dpplabs.project.cermatigithubuser.presentation.view.otherview.PermissionManager
import com.dpplabs.project.cermatigithubuser.presentation.view.otherview.SuggestionManager
import com.dpplabs.project.cermatigithubuser.presentation.view.view.UserListView
import com.dpplabs.project.cermatigithubuser.presentation.view.view.UserView
import com.dpplabs.project.cermatigithubuser.presentation.view.viewholder.MainViewHolder
import java.net.URL

class MainActivity : AppCompatActivity(), Coordinator {

    private var presenter: CoordinatorPresenter? = null
    private var permissionManager: PermissionManager? = null
    private var suggestionsManager: SuggestionManager? = null
    private var viewHolder: MainViewHolder? = null
    private var userContainer: ViewContainer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewHolder = MainViewHolder(findViewById(R.id.activity_main) as ViewGroup)
        permissionManager = PermissionManager(this)
        permissionManager!!.requestBasePermissions(this) {
            presenter = CoordinatorPresenter(AndroidInterface(this@MainActivity))
            presenter!!.setView(this@MainActivity)
            presenter!!.start()
        }
    }

    override fun init() {
        val onStartBtn = View.OnClickListener { presenter!!.onStartButton() }
        viewHolder!!.helloContent.setOnClickListener(onStartBtn)
        viewHolder!!.searchImage.setOnClickListener(onStartBtn)
        suggestionsManager = SuggestionManager(viewHolder!!.searchView)
        suggestionsManager!!.init()
        viewHolder!!.searchView.setOnSearchListener(object : FloatingSearchView.OnSearchListener {
            override fun onSuggestionClicked(searchSuggestion: SearchSuggestion) {
                presenter!!.onSearchInput(searchSuggestion.body)
            }

            override fun onSearchAction(currentQuery: String) {
                suggestionsManager!!.saveSuggestion(currentQuery.trim { it <= ' ' })
                presenter!!.onSearchInput(currentQuery)
            }
        })
        viewHolder!!.searchView.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.favorites -> {
                    viewHolder!!.searchView.clearQuery()
                    viewHolder!!.searchView.clearSearchFocus()
                    presenter!!.onFavoritesMenuClick()
                }
            }
        }
        viewHolder!!.searchView.setOnQueryChangeListener { _, newQuery -> suggestionsManager!!.suggest(newQuery) }
        viewHolder!!.searchView.setOnFocusChangeListener(object : FloatingSearchView.OnFocusChangeListener {
            override fun onFocus() {
                suggestionsManager!!.suggest(viewHolder!!.searchView.query)
            }

            override fun onFocusCleared() {

            }
        })
        userContainer = ViewContainer(viewHolder!!.viewContainer)
    }

    override fun openSearchView() {
        viewHolder!!.searchImage.setOnClickListener(null)
        viewHolder!!.helloContent.setOnClickListener(null)
        AnimationManager.openSearchView(viewHolder) { viewHolder!!.searchView.setSearchFocused(true) }
    }

    override fun openUserList(presenter: UserListPresenter) {
        UserListView(ViewContainer(viewHolder!!.userSearchContent), presenter).open()
    }

    override fun openUser(presenter: UserPresenter) {
        UserView(userContainer!!, presenter).open()
    }

    override fun openBrowser(url: URL) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url.toString())))
    }

    override fun closeView() {
        finish()
    }

    override fun closeUser() {
        userContainer!!.closeView()
    }

    override val isUserOpened: Boolean
        get() = userContainer!!.viewOpened()

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        permissionManager!!.onPermissionCallback(requestCode, permissions, grantResults)
    }

    override fun onBackPressed() {
        presenter!!.onBackPressed()
    }
}
