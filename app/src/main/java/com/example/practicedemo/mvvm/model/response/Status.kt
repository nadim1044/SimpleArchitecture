package com.example.practicedemo.mvvm.model.response

data class Status(
    val count: Int,
    val device_datetime_sent: String,
    val device_datetime_used: String,
    val message: Any,
    val method: String,
    val request_method: String,
    val state: String,
    val territory: String,
    val version: String
)