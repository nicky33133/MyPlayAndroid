package com.example.myplayandroid.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel(){
    private val pageLiveData= MutableLiveData<Int>()

    fun setPage(page: Int){ //设置页码的值
        pageLiveData.value=page
    }
    fun getPage(): Int?{//获取页码的值
        return pageLiveData.value
    }
}