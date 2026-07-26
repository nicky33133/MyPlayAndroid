package com.example.core.view.base.lce

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

//在Activity或Fragment中初始化需要的函数。
interface BaseInit{
//    fun initData()//加载数据
    fun initView()//加载视图
}
interface BaseActivityInit: BaseInit{
    //返回自己的界面布局
    fun getLayoutView(): View
}
interface BaseFragmentInit: BaseInit{
    //返回自己的界面布局
    fun getLayoutView(inflater: LayoutInflater,container: ViewGroup?,attachToRoot: Boolean): View
}