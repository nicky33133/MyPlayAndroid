package com.example.myplayandroid.home.article

import android.app.Application
import androidx.lifecycle.liveData
import com.example.model.room.PlayDatabase
import com.example.myplayandroid.base.liveDataFire
import com.example.myplayandroid.base.liveDataModel
import com.example.network.base.PlayAndroidNetWork
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject


@ActivityRetainedScoped
class SearchRepository  @Inject constructor(application: Application){
    private val hotKeyDao = PlayDatabase.getDatabase(application).hotKeyDao()

    //获取搜索热词
    fun getHotKey() = liveDataFire { //liveDataFire与liveDataModel区别开

        val hotKeyList = hotKeyDao.getHotKeyList()

        if (hotKeyList.isNotEmpty()){
            Result.success(hotKeyList)
        }else{
            //网络下载的
            val projectHotKey= PlayAndroidNetWork.getHotKey()
            if (projectHotKey.errorCode == 0){
                val hotKeyLists = projectHotKey.data

                hotKeyDao.insertList(hotKeyLists)//插入下载的数据
                Result.success(hotKeyLists)
            }else{
                Result.failure(RuntimeException("response status is ${projectHotKey.errorCode}  msg is ${projectHotKey.errorMsg}"))

            }
        }
    }

    //获取搜索结果
    fun getQueryArticleList(page: Int,k: String) = liveDataModel {//liveDataFire与liveDataModel区别开
        PlayAndroidNetWork.getQueryArticleList(page,k)
    }
}