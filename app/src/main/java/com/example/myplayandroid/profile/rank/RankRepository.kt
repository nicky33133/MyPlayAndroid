package com.example.myplayandroid.profile.rank

import com.example.myplayandroid.base.liveDataModel
import com.example.network.base.PlayAndroidNetWork
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped //（作用域注解）
//这是 Hilt 提供的一个作用域（Scope）。它绑定的对象的生命周期会和当前 Activity 的生命周期保持一致，
// 但有一个关键特性——它会绕过屏幕旋转（Configuration Change）。

// @Inject constructor()构造函数注入:
//告诉 Hilt 依赖注入框架：“当我需要创建一个 RankRepository 的实例时，请直接调用这个空的构造函数来生成对象即可。
class RankRepository  @Inject constructor(){

    //获取排行榜列表
    fun getRankList(page: Int)= liveDataModel {
        PlayAndroidNetWork.getRankList(page)
    }

    //获取个人积分获取列表
    fun getUserRank(page: Int)=liveDataModel {
        PlayAndroidNetWork.getUserRank(page)
    }


    //获取个人积分信息
    fun getUserInfo()=liveDataModel { PlayAndroidNetWork.getUserInfo() }


}