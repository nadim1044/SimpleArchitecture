package com.example.practicedemo.mvvm.view.activity

import com.example.practicedemo.R
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.PersistableBundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.*

open class BaseActivity : AppCompatActivity() {

    var dialog: Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState )
    }

    fun showDialog() {
        dialog = Dialog(this, R.style.DialogCustom)
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog?.setContentView(R.layout.custom_progress)
        dialog?.setCancelable(false)
        dialog?.show()
    }


    fun hideDialog() {
        Handler(mainLooper).postDelayed({
            dialog?.dismiss()
        },50)
    }

    fun showToastShort(msg:String){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show()
    }
}