package com.example.practicedemo.mvvm.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.practicedemo.mvvm.model.response.LoginResponse
import com.example.practicedemo.webService.APIError
import com.example.practicedemo.webService.DataWrapper
import com.example.practicedemo.webService.RetrofitFactory
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response
import java.net.ConnectException

class MainViewModel : ViewModel(){

    val name="Nadim Ansari"
    private val tag: String =this::class.java.simpleName
    private val service = RetrofitFactory.get()

     fun login(email:String,password:String): LiveData<DataWrapper<LoginResponse>> {
        val mDataWrapperRegister = DataWrapper<LoginResponse>()
        val mResponseData = MutableLiveData<DataWrapper<LoginResponse>>()

        CoroutineScope(Dispatchers.IO).launch {

            var response: Response<LoginResponse>? = null
            try {
                response = service.login(email,password)

                withContext(Dispatchers.Main) {
                    try {
                        if (response.isSuccessful) {

                            mDataWrapperRegister.data = response.body()
                            mResponseData.value = mDataWrapperRegister

                            //Do something with response e.g show to the UI.
                        } else {
                            Log.e(tag, "Error :: " + response.code())

                            val g: Gson = Gson()
                            val errorRes: LoginResponse = g.fromJson(
                                response.errorBody()!!.string(),
                                LoginResponse::class.java
                            )

                            mDataWrapperRegister.apiError =
                                APIError(response.code(), response.message())
                            mResponseData.value = mDataWrapperRegister
                        }
                    } catch (e: HttpException) {
                        Log.e(tag, "HttpException :: " + e.message)
                        mDataWrapperRegister.apiError = APIError(100, response.message())
                        mResponseData.value = mDataWrapperRegister
                    } catch (e: Throwable) {
                        Log.e(tag, "Throwable :: " + e.message)
                        mDataWrapperRegister.apiError = APIError(100, response.message())
                        mResponseData.value = mDataWrapperRegister
                    } catch (e: ConnectException) {
                        Log.e(tag, "ConnectException :: " + e.message)

                        mDataWrapperRegister.apiError = APIError(100, response.message())
                        mResponseData.value = mDataWrapperRegister
                    }
                }

            } catch (e: Throwable) {
                Log.e(tag, "Error! ${e.message}")
                if (response != null)
                    mDataWrapperRegister.apiError = APIError(100, "Something went wrong")
                else
                    mDataWrapperRegister.apiError = APIError(100, e.message!!)

                withContext(Dispatchers.Main) {
                    mResponseData.value = mDataWrapperRegister
                }
            }

        }
        return mResponseData
    }

}