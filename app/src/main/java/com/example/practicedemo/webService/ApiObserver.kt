package com.example.practicedemo.webService

import androidx.lifecycle.Observer

class ApiObserver<T>(private val mChangeListener: ChangeListener<T>) :
    Observer<DataWrapper<T>?> {
    override fun onChanged(mDataWrapper: DataWrapper<T>?) {
        if (mDataWrapper != null) {
            if (mDataWrapper.apiError != null) {
                mChangeListener.onError(mDataWrapper.apiError)
            } else {
                mChangeListener.onSuccess(mDataWrapper.data!!)
            }
        }
    }

    interface ChangeListener<T> {
        fun onSuccess(dataWrapper: T)
        fun onError(error: APIError?)
    }

    companion object {
        private const val ERROR_CODE = 0
    }
}