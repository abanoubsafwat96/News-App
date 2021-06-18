package com.abanoub.news.main

import android.app.Application
import com.abanoub.marvel.data.remote.BaseCallBack
import com.abanoub.marvel.data.remote.SearchArticleRepositoryImp
import com.abanoub.news.data.repository.SearchArticleRepository
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule


class MainViewModelTest{

    @Test
    fun `search() with empty searchKey then don't call SearchArticleRepository`(){
        //arrange
        var hitServer=false
        val searchKey=""
        val repository=object :SearchArticleRepository{
            override fun search(
                searchKey: String,
                pageNum: Int,
                errCallback: BaseCallBack,
                callback: SearchArticleRepositoryImp.OnCallback
            ) {
                hitServer=true
            }
        }

        //act
        MainViewModel(Application()).search(repository,searchKey,true)

        //assert
        assertFalse(hitServer)
    }
}