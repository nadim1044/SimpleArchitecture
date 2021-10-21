package com.example.practicedemo.mvvm.view.activity

import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.practicedemo.R
import com.example.practicedemo.databinding.ActivityMainBinding
import com.example.practicedemo.mvvm.model.response.LoginResponse
import com.example.practicedemo.mvvm.viewModel.MainViewModel
import com.example.practicedemo.utils.ConnectivityReceiver
import com.example.practicedemo.webService.APIError
import com.example.practicedemo.webService.ApiObserver
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : BaseActivity() {

     val viewModel: MainViewModel by lazy { MainViewModel() }
    lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<ActivityMainBinding>(
            this,
            R.layout.activity_main
        ).also {
            binding = it
            binding.main = viewModel
        }
        doLogin()

    }

    private fun doLogin() {
        if (ConnectivityReceiver.isNetworkConnected(this)){
            showDialog()

                viewModel?.login("nadim@yopmail.com","123456")
                    ?.observe(this@MainActivity, ApiObserver<LoginResponse>(object :
                        ApiObserver.ChangeListener<LoginResponse> {

                        override fun onError(error: APIError?) {

                            Log.e("Response", "Fail ${error!!.httpErrorMessage}")
                            hideDialog()
                            showToastShort("Error")
                        }

                        override fun onSuccess(dataWrapper: LoginResponse) {
                            hideDialog()
                            showToastShort("Success")
                        }
                    }))


        }else{
            showToastShort("No Internet")
        }

    }
}