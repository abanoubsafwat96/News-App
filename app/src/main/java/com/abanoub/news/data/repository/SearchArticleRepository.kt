package com.abanoub.news.data.repository

import com.abanoub.marvel.data.remote.BaseCallBack
import com.abanoub.marvel.data.remote.SearchArticleRepositoryImp

interface SearchArticleRepository {
    fun search(
        searchKey: String,
        pageNum:Int,
        errCallback: BaseCallBack,
        callback: SearchArticleRepositoryImp.OnCallback
    )
}