package com.abanoub.news.data

import com.abanoub.news.data.ApiResponse
import com.abanoub.news.data.model.Article
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Services {

    @GET("everything")
    fun getArticles(
        @Query("q") searchKey: String,
        @Query("sortBy") sortBy: String,
        @Query("apiKey") apiKey: String,
        @Query("page") page: Int
    ): Call<ApiResponse<Article>>
}