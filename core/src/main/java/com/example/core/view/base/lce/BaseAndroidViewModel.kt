package com.example.core.view.base.lce

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
//import java.security.Key

abstract class BaseAndroidViewModel<BaseData,Data,Key>: ViewModel() {

    //BaseData,Data,Key>是三个自定义的泛型参数
    //继承的子类必须传入这三个参数

    val dataList= ArrayList<Data>()


    //记录错误点：传入参数key时，没有使用抽象方法BaseAndroidViewModel的泛型参数
    //而是导入了一个系统的，导致错误
    private val pageLiveData= MutableLiveData<Key>()

    // dataLiveData是数据源
    val dataLiveData=pageLiveData.switchMap {
        page-> getData(page)
    }

    //dataLiveData与getData方法有关
    //接下来看getData的实现
    abstract fun getData(page: Key): LiveData<Result<BaseData>>

    fun getDataList(page: Key){
        //给pageLiveData设置值
        pageLiveData.value=page!!
    }
}