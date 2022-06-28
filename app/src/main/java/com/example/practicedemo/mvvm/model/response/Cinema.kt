package com.example.practicedemo.mvvm.model.response

data class Cinema(
    val cinema_id: Int,
    val cinema_name: String,
    val distance: Double,
    val logo_url: String,
    val showings: Showings
)