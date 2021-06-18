package com.abanoub.news.base

import android.app.Application
import android.graphics.Movie
import androidx.lifecycle.MutableLiveData


open class PaginationBaseViewModel(application: Application) : BaseViewModel(application) {

    protected var pageNum = 1
    protected var isaLastPage = false
    private val onLoadMoreItems = MutableLiveData<Any>()
    private val productsLiveData =
        MutableLiveData<List<Movie>>()

    fun getOnLoadMoreItems(): MutableLiveData<Any>? {
        return onLoadMoreItems
    }

    fun isaLastPage(): Boolean {
        return isaLastPage
    }

    fun getProductsLiveData(): MutableLiveData<List<Movie>>? {
        return productsLiveData
    }
    open fun isLoading(): Boolean {
        return loadingProgress.value != null && loadingProgress.value!!
    }
}