package com.example.myplayandroid.main

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.activity.viewModels
import com.example.core.view.base.lce.BaseActivity
import com.example.myplayandroid.R
import com.example.myplayandroid.databinding.ActivityMainBinding
import com.example.myplayandroid.main.MainViewModel
import com.example.myplayandroid.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlin.getValue
import kotlin.system.exitProcess

@AndroidEntryPoint
class MainActivity : BaseActivity(){
    private lateinit var binding: ActivityMainBinding

    //MainViewModel自定义类，其中定义了获取页面的方法
    private val viewModel by viewModels<MainViewModel>()
    var isPort=true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        setContentView(R.layout.activity_main)
//        binding= ActivityMainBinding.inflate(layoutInflater)
//        setContentView(binding.root)
    }



    override fun initData() {

    }

    companion object{
        fun actionStart(context: Context){
            val intent= Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }
    }

    //视图初始化
    override fun initView(){
        //resources.configuration.orientation 返回当前设备的屏幕方向
        //Configuration.ORIENTATION_PORTRAIT 是竖屏常量（值为 1）
        isPort=resources.configuration.orientation== Configuration.ORIENTATION_PORTRAIT
        binding.homeView?.init(supportFragmentManager,viewModel)

    }

    //加载视图
    override fun getLayoutView(): View {
        binding= ActivityMainBinding.inflate(layoutInflater)
        return binding.root
    }


    //监听物理按键事件，拦截返回键实现双击退出功能
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode== KeyEvent.KEYCODE_BACK){
            exit()
            return false
        }
        return super.onKeyDown(keyCode, event)
    }

    // 时间戳变量，用于记录用户第一次按下返回键的时刻
    private var exitTime: Long=0

    private fun exit(){
        if (System.currentTimeMillis()-exitTime>2000){
            showToast(getString(R.string.exit_program))
            exitTime= System.currentTimeMillis()
        }else{
            exitProcess(0)//退出进程

        }

    }
}