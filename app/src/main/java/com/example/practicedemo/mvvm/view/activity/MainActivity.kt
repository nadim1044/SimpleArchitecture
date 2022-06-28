package com.example.practicedemo.mvvm.view.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.practicedemo.R
import com.example.practicedemo.databinding.ActivityMainBinding
import com.example.practicedemo.mvvm.model.response.LoginResponse
import com.example.practicedemo.mvvm.model.response.MovieResponseX
import com.example.practicedemo.mvvm.model.response.userlist
import com.example.practicedemo.mvvm.viewModel.MainViewModel
import com.example.practicedemo.repository.PostRepositoty
import com.example.practicedemo.utils.ConnectivityReceiver
import com.example.practicedemo.viewmodel.PostViewmodel
import com.example.practicedemo.viewmodel.Postviewmodelfactory
import com.example.practicedemo.webService.APIError
import com.example.practicedemo.webService.ApiObserver
import com.example.practicedemo.webService.RetrofitFactory

class MainActivity : BaseActivity() {

    val viewModel: MainViewModel by lazy { MainViewModel() }
    lateinit var binding: ActivityMainBinding
    lateinit var postViewmodel: PostViewmodel


    @SuppressLint("StringFormatMatches")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<ActivityMainBinding>(
            this,
            R.layout.activity_main
        ).also {
            binding = it
            binding.main = viewModel
        }
        val postservice = RetrofitFactory.rectr
        val repo = PostRepositoty(postservice)
        postViewmodel =
            ViewModelProvider(this, Postviewmodelfactory(repo)).get(PostViewmodel::class.java)
//        doLogin()
//        getfilmShowTimes()

        getPost()

    }

    private fun doLogin() {
        if (ConnectivityReceiver.isNetworkConnected(this)) {
            showDialog()

            viewModel?.login("nadim@yopmail.com", "123456")
                .observe(this@MainActivity, ApiObserver<LoginResponse>(object :
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


        } else {
            showToastShort("No Internet")
        }

    }

    private fun getPost() {
        if (ConnectivityReceiver.isNetworkConnected(this)) {
            showDialog()
            postViewmodel.getPost().observe(
                this@MainActivity,
                ApiObserver<userlist>(object : ApiObserver.ChangeListener<userlist> {
                    override fun onSuccess(dataWrapper: userlist) {
                        hideDialog()
                        Log.e("tag","Success")
                        val mainResponse = dataWrapper.data
                        Log.e("tag",mainResponse.toString())
                        Toast.makeText(this@MainActivity, mainResponse.toString(), Toast.LENGTH_SHORT).show()
                    }

                    override fun onError(error: APIError?) {
                        hideDialog()
                        Toast.makeText(this@MainActivity, "ERROR mainactivity", Toast.LENGTH_SHORT).show()
                        Log.e("tag","ERROR mainactivity + ${error?.httpErrorMessage}")
                    }

                })
            )

        } else {
            Toast.makeText(this, "No Internet", Toast.LENGTH_SHORT).show()
        }
    }


    private fun getfilmShowTimes() {
        if (ConnectivityReceiver.isNetworkConnected(this)) {
            showDialog()
            viewModel.filmShowTimes(
                "-22.0;14.0",
                "DEMO_5",
                "dhZkUIBhhp9JodzKz8PuU7tzQH3HRzdD6nCbBoJX",
                "Basic REVNT181X1hYOlBGaVNQbkZoZmROSw==",
                "XX",
                "v200",
                "2022-06-13T10:12:55.182Z",
                2,
                "7772",
                "2022-06-15"

            ).observe(
                this@MainActivity,
                ApiObserver<MovieResponseX>(object : ApiObserver.ChangeListener<MovieResponseX> {
                    override fun onError(error: APIError?) {
                        Log.e("Response", "Fail ${error!!.httpErrorMessage}")
                        hideDialog()
                        showToastShort("Error")
                    }

                    override fun onSuccess(dataWrapper: MovieResponseX) {
                        hideDialog()
                        showToastShort("Success")

//                    for cinemas
                        val cinemaname = arrayListOf<String>()
                        dataWrapper.cinemas.forEach {
                            cinemaname.add(it.cinema_name)
                        }
                        Log.e("cinemas", cinemaname.toString())

                        dataWrapper.cinemas.forEach {
                            Log.e("TAG", it.showings.toString())
                            Log.e("TAG", it.showings.Standard.times.get(0).start_time.toString())
                        }


//                    for timings
                        val timeings = arrayListOf<String>()
                        dataWrapper.cinemas.forEach {
                            it.showings.Standard.times.forEach {
                                timeings.add(it.start_time)
                            }
                        }
                        Log.e("timings", timeings.toString())

                        dataWrapper.cinemas.forEach {

                        }

                    }
                })
            )

        } else {
            showToastShort("No Internet")
        }

    }
}