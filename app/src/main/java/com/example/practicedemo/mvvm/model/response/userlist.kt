package com.example.practicedemo.mvvm.model.response

data class userlist(
    var page: Int,
    var per_Page: Int,
    var total: Int? = null,
    var total_pages: Int,
    var data: List<posts>
)
