package com.example.myplayandroid.profile.share

import com.example.myplayandroid.base.liveDataModel
import com.example.network.base.PlayAndroidNetWork
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject


@ActivityRetainedScoped
class ShareRepository @Inject constructor(){

    fun getMyShareList(page: Int)= liveDataModel {
        PlayAndroidNetWork.getMyShareList(page)
    }

    fun getShareList(cid: Int,page:Int)=liveDataModel {
        PlayAndroidNetWork.getShareList(cid,page)
    }


}