package com.example.practicedemo.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practicedemo.mvvm.model.response.LoginResponse
import com.example.practicedemo.mvvm.model.response.userlist
import com.example.practicedemo.repository.PostRepositoty
import com.example.practicedemo.webService.APIError
import com.example.practicedemo.webService.DataWrapper
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException
import retrofit2.Response
import java.net.ConnectException

class PostViewmodel(val postRepositoty: PostRepositoty) : ViewModel() {

    fun getPost(): LiveData<DataWrapper<userlist>> {
        val mDataWrapperpost = DataWrapper<userlist>()
        val mResponseData = MutableLiveData<DataWrapper<userlist>>()



        viewModelScope.launch {
            var response: Response<userlist>? = null

            try{

                    response = postRepositoty.getpost()
                    try {
                        if (response!!.isSuccessful){
                            mDataWrapperpost.data = response.body()
                            mResponseData.value = mDataWrapperpost


                        }
                        else{
                            Log.e("tag","Error ::" + response.code() + response.message())
                            val g : Gson = Gson()
                            val errorRes : userlist = g.fromJson(
                                response.errorBody()?.string(),
                                userlist::class.java
                            )
                            mDataWrapperpost.apiError = APIError(response.code(),response.message())
                            mResponseData.value = mDataWrapperpost
                        }
                    }
                    catch (e : HttpException){
                        Log.e("tag", "HttpException :: " + e.message)
                        mDataWrapperpost.apiError = APIError(100, response.message())
                        mResponseData.value = mDataWrapperpost
                    }
                    catch (e:Throwable){
                        Log.e("tag", "Throwable :: " + e.message)
                        mDataWrapperpost.apiError = APIError(100, response.message())
                        mResponseData.value = mDataWrapperpost
                    }
                    catch (e:ConnectException){
                        Log.e("tag", "ConnectException :: " + e.message)

                        mDataWrapperpost.apiError = APIError(100, response.message())
                        mResponseData.value = mDataWrapperpost
                    }



            }catch (e : Throwable){
                Log.e("tag", "ERROR ${e.message}")
                if (response != null){
                    mDataWrapperpost.apiError = APIError(100,"Something went wrong")
                } else{
                    mDataWrapperpost.apiError = APIError(100,e.message!!)
                }
                mResponseData.value = mDataWrapperpost
            }

        }

        return mResponseData
    }

}