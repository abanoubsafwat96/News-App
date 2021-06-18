package com.abanoub.news.main

import android.app.Application
import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.MutableLiveData
import com.abanoub.marvel.data.remote.SearchArticleRepositoryImp
import com.abanoub.news.base.PaginationBaseViewModel
import com.abanoub.news.data.model.Article
import com.abanoub.news.data.repository.SearchArticleRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.ArrayList
import kotlin.coroutines.CoroutineContext

class MainViewModel(application: Application) : PaginationBaseViewModel(application),
    CoroutineScope {
    var onSearchLiveData = MutableLiveData<ArrayList<Article>>()

    override val coroutineContext: CoroutineContext = Dispatchers.Main
    val watcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) = Unit
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

        private var searchFor = ""
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val searchText = s.toString().trim()
            if (searchText == searchFor)
                return

            searchFor = searchText

            launch {
                delay(300)  //debounce timeOut
                if (searchText != searchFor) //disable search if user still typing for more than 300 ms
                    return@launch

                search(SearchArticleRepositoryImp(), s.toString(), true)
            }
        }
    }

    fun search(repository: SearchArticleRepository, searchKey: String, shouldClearList: Boolean) {
        if (searchKey == "") return

        paginationLoadingProgress.value = true
        repository.search(
            searchKey,
            if (shouldClearList) 1 else pageNum + 1,
            this,
            object : SearchArticleRepositoryImp.OnCallback {
                override fun onCallback(body: ArrayList<Article>?) {
                    paginationLoadingProgress.value = false
                    pageNum = if (shouldClearList) 1 else pageNum + 1

                    val value: ArrayList<Article>? = onSearchLiveData.getValue()
                    if (value == null || shouldClearList) {
                        onSearchLiveData.setValue(body)
                    } else {
                        value.addAll(body!!)
                        onSearchLiveData.setValue(value)
                    }
                }
            })
    }
}