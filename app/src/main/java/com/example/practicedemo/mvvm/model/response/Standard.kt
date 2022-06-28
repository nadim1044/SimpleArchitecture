package com.example.practicedemo.mvvm.model.response

data class Standard(
    val film_id: Int,
    val film_name: String,
    val times: List<Time>
)