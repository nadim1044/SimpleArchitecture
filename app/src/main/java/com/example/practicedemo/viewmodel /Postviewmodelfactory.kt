package com.example.practicedemo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.practicedemo.repository.PostRepositoty

class Postviewmodelfactory(val postRepositoty: PostRepositoty) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PostViewmodel(postRepositoty) as T
    }
}