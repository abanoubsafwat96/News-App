package com.abanoub.news.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.abanoub.marvel.data.remote.SearchArticleRepositoryImp
import com.abanoub.news.R
import com.abanoub.news.base.BaseActivity
import com.abanoub.news.data.model.Article
import com.abanoub.news.details.DetailedActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar_layout.*
import org.koin.android.ext.android.get
import org.koin.android.ext.android.inject

class MainActivity : BaseActivity<MainViewModel>() {
    private val mainViewModel by inject<MainViewModel>()
    private lateinit var searchList: ArrayList<Article>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        search_Et.addTextChangedListener(mainViewModel.watcher)

        searchList = ArrayList<Article>()
        recyclerView.adapter = SearchAdapter(searchList, object : SearchAdapter.OnItemClick {
            override fun onItemClicked(article: Article) {
                startDetailedActivity(article)
            }
        })

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollVertically(1)) {
                    if (mainViewModel.isLoading() || mainViewModel.isaLastPage()) {
                        return
                    }
                    mainViewModel.search(SearchArticleRepositoryImp(), search_Et.text.toString(),false)
                }
            }
        })

        mainViewModel.paginationLoadingProgress.observe(this, Observer { aBoolean ->
            if (aBoolean)
                setPaginationProgressViewVisibility(View.VISIBLE)
            else
                setPaginationProgressViewVisibility(View.GONE)
        })

        if (isConnected()) {
            mainViewModel.onSearchLiveData.observe(this, Observer { articles ->
                if (articles == null) return@Observer

                searchList.clear()
                searchList.addAll(articles)
                recyclerView.getAdapter()!!.notifyDataSetChanged()
            })
        }
    }

    private fun startDetailedActivity(article: Article) {
        var intent = Intent(this, DetailedActivity::class.java)
        intent.putExtra("article", article)
        startActivity(intent)
    }

    fun setPaginationProgressViewVisibility(value: Int) {
        progressBar.setVisibility(value)
    }

    override fun getViewModel(): MainViewModel {
        return mainViewModel
    }
}