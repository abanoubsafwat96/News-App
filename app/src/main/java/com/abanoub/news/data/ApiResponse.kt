package com.abanoub.news.data

data class ApiResponse<T>(
    val articles: ArrayList<T>
)