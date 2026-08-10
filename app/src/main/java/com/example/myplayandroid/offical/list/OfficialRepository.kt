package com.example.myplayandroid.offical.list

import android.app.Application
import com.example.core.util.MMKVUtils
import com.example.model.pojo.QueryArticle
import com.example.model.room.PlayDatabase
import com.example.model.room.entity.OFFICIAL
import com.example.myplayandroid.base.liveDataFire
import com.example.myplayandroid.home.DOWN_OFFICIAL_ARTICLE_TIME
import com.example.myplayandroid.home.FOUR_HOUR
import com.example.network.base.PlayAndroidNetWork
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class OfficialRepository @Inject constructor(
    application: Application
){
    private val projectClassifyDao =  PlayDatabase.getDatabase(application).projectClassifyDao()

    private val articleListDao = PlayDatabase.getDatabase(application).browseHistoryDao()

    //获取公众号标题列表
    fun getWxArticleTree(isRefresh: Boolean)= liveDataFire {
        val projectClassifyLists = projectClassifyDao.getAllOfficial()
        if (projectClassifyLists.isNotEmpty() && !isRefresh){//并且不是刷新状态
            //为什么需要 !isRefresh？
            //当用户下拉刷新时，期望看到的是最新的服务器数据，而不是旧数据。
            //如果此时本地数据库里恰好有旧数据（比如上次同步时保存的），
            // 若不加 !isRefresh，代码会直接返回这些旧数据，导致刷新操作看起来“无效”或“没变化”。

            Result.success(projectClassifyLists)
        }else{
            val projectWxArticleTree= PlayAndroidNetWork.getWxArticleTree()
            if (projectWxArticleTree.errorCode == 0) {
                val projectList = projectWxArticleTree.data
                Result.success(projectList)
            }else{
                Result.failure(RuntimeException("response status is ${projectWxArticleTree.errorCode}  msg is ${projectWxArticleTree.errorMsg}"))
            }
        }

    }

    //获取具体公众号文章列表
    fun getWxArticle(query: QueryArticle)=liveDataFire {

        //数据存储状态的

        if (query.page == 1){

//            val mmkv= MMKVUtils//单例类直接用

            val articleListForChapterId =
                articleListDao.getArticleListForChapterId(OFFICIAL, query.cid)

            var downArticleTime = 0L

            MMKVUtils.getData(DOWN_OFFICIAL_ARTICLE_TIME, System.currentTimeMillis())


            if (articleListForChapterId.isNotEmpty() && downArticleTime >0  && downArticleTime - System.currentTimeMillis() < FOUR_HOUR && !query.isRefresh){
                Result.success(articleListForChapterId)
            }else{
                //网络下载
                val projectWxArticle= PlayAndroidNetWork.getWxArticle(query.page,query.cid)
                if (projectWxArticle.errorCode == 0){
                   //数据库的
                    if (articleListForChapterId.isNotEmpty() && articleListForChapterId[0].link == projectWxArticle.data.datas[0].link && !query.isRefresh){
                        Result.success(articleListForChapterId)
                    }else{
                        projectWxArticle.data.datas.forEach {
                            it.localType= OFFICIAL
                        }
                    }
                    MMKVUtils.getData(DOWN_OFFICIAL_ARTICLE_TIME, System.currentTimeMillis())
                    if (query.isRefresh){
                        //数据库删除
                        articleListDao.deleteAll(OFFICIAL, query.cid)
                    }
                    //数据库插入数据
                    articleListDao.insertList(projectWxArticle.data.datas)

                    Result.success(projectWxArticle.data.datas)
                }else{
                    Result.failure(RuntimeException("response status is ${projectWxArticle.errorCode}   msg is ${projectWxArticle.errorMsg}"))
                }
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