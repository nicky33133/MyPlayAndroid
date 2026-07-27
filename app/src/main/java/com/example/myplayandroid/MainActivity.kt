package com.example.myplayandroid

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.example.core.view.base.lce.BaseActivity
import com.example.myplayandroid.databinding.ActivityMainBinding
import com.example.myplayandroid.main.MainViewModel
import kotlin.getValue

class MainActivity : BaseActivity(){


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        setContentView(R.layout.activity_main)
//        binding= ActivityMainBinding.inflate(layoutInflater)
//        setContentView(binding.root)
    }
    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<MainViewModel>()
    var isPort=true
    companion object{
        fun actionStart(context: Context){
            val intent= Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun initView(){

        isPort=resources.configuration.orientation== Configuration.ORIENTATION_PORTRAIT
        when(isPort){
            true->binding.homeView?.init(supportFragmentManager,viewModel)
            false -> binding.homeView?.init(supportFragmentManager, viewModel)
        }
    }

    //加载视图
    override fun getLayoutView(): View {
        binding= ActivityMainBinding.inflate(layoutInflater)
        return binding.root
    }
}