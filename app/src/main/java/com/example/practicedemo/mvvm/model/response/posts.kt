package com.example.practicedemo.mvvm.model.response

data class posts(
    val id : Int = 0,
    val email: String = "",
    val first_name: String = "",
    val last_name: String = "",
    var avatar:String?=null
)
