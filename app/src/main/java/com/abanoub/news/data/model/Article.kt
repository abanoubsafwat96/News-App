package com.abanoub.news.data.model

import java.io.Serializable

data class Article(
    var author: String,
    var title: String,
    var description: String,
    var url: String,
    var urlToImage: String,
    var publishedAt: String,
    var source: Source,
    var content: String
):Serializable