package com.abanoub.marvel.data.remote

import com.abanoub.news.data.ApiResponse
import com.abanoub.news.data.Client
import com.abanoub.news.data.ResponseWrapper
import com.abanoub.news.data.Services
import com.abanoub.news.data.model.Article
import com.abanoub.news.data.repository.SearchArticleRepository

class SearchArticleRepositoryImp : SearchArticleRepository {

    override fun search(
        searchKey: String,
        pageNum: Int,
        errCallback: BaseCallBack,
        callback: OnCallback
    ) {
        Client.getInstance().create(Services::class.java)
            .getArticles(searchKey, Client.PUBLISHED_AT, Client.API_KEY, pageNum)
            .enqueue(object : ResponseWrapper<ApiResponse<Article>>(errCallback) {
                override fun onSuccessCase(body: ApiResponse<Article>) {
                    callback.onCallback(body.articles)
                }

            })
    }

    interface OnCallback {
        fun onCallback(body: ArrayList<Article>?)
    }
}