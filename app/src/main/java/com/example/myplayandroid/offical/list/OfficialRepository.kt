package com.example.myplayandroid.offical.list

import android.app.Application
import com.example.model.pojo.QueryArticle
import com.example.model.room.entity.OFFICIAL
import com.example.myplayandroid.base.liveDataFire
import com.example.network.base.PlayAndroidNetWork
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class OfficialRepository @Inject constructor(
    application: Application
){

    //获取公众号标题列表
    fun getWxArticleTree(isRefresh: Boolean)= liveDataFire {
        val projectWxArticleTree= PlayAndroidNetWork.getWxArticleTree()
        if (projectWxArticleTree.errorCode == 0){
            val projectList=projectWxArticleTree.data
            Result.success(projectList)
        }else{
            Result.failure(RuntimeException("response status is ${projectWxArticleTree.errorCode}  msg is ${projectWxArticleTree.errorMsg}"))
        }
    }


    //获取具体公众号文章列表
    fun getWxArticle(query: QueryArticle)=liveDataFire {
        if (query.page == 1){
            val projectWxArticle= PlayAndroidNetWork.getWxArticle(query.page,query.cid)
            if (projectWxArticle.errorCode == 0){
                projectWxArticle.data.datas.forEach {
                    it.localType= OFFICIAL
                }

                Result.success(projectWxArticle.data.datas)
            }else{
                Result.failure(RuntimeException("response status is ${projectWxArticle.errorCode}   msg is ${projectWxArticle.errorMsg}"))
            }
        }else{
            val projectTreeArticle= PlayAndroidNetWork.getWxArticle(query.page,query.cid)
            if (projectTreeArticle.errorCode == 0){
                Result.success(projectTreeArticle.data.datas)
            }else{
                Result.failure(RuntimeException("response status is ${projectTreeArticle.errorCode}  mas is ${projectTreeArticle.errorMsg}"))
            }
        }

    }
}