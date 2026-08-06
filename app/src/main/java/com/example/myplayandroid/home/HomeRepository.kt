package com.example.myplayandroid.home

import android.app.Application
import android.util.Log
import com.example.core.util.MMKVUtils
import com.example.model.pojo.QueryHomeArticle
import com.example.model.room.entity.Article
import com.example.model.room.entity.HOME
import com.example.model.room.entity.HOME_TOP
import com.example.myplayandroid.base.liveDataFire
import com.example.network.base.PlayAndroidNetWork
import com.tencent.mmkv.MMKV
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject
//import dagger.hilt.android.scopes.ActivityRetainedScoped
//import javax.inject.Inject
import kotlin.contracts.contract


//@ActivityRetainedScoped
//@ActivityRetainedScoped：这是 Hilt 的作用域注解，
//表示该 Repository 的生命周期与 Activity 保持一致，并且在配置变更（如旋转屏幕）时不会重新创建
class HomeRepository @Inject constructor(
//    @Inject constructor：通过构造器注入，
//由 Hilt 提供 Application 实例，方便获取全局上下文
    val application: Application
) {
    companion object{
        private const val TAG = "HomeRepository"
    }

//    companion object{
//        private const val TAG="HomeRepository"
//    }

//    // 获取banner
//    fun getBanner()=liveDataFire {
//        // 第一步：从 MMKV 读取上次下载时间（同步）
//        val mmkv= MMKVUtils
//        // 若键不存在，默认返回当前时间戳
//        val downImageTime=mmkv.getData(DOWN_IMAGE_TIME, System.currentTimeMillis())
//
////        val bannerBeanDao=
//
//    }

    //先写这个
    //QueryHomeArticle是一个数据类
    //liveDataFire是一个封装的方法（有关liveData）
    fun getArticleList(query: QueryHomeArticle) = liveDataFire {
        coroutineScope {
            val res = arrayListOf<Article>()
            if (query.page == 1) {
                //?
                val kv = MMKVUtils
                var downTopArticleTime = 0L
                //这行代码会立即返回结果，无需挂起，适用于任何地方（包括主线程）
                downTopArticleTime =
                    MMKV.defaultMMKV().decodeLong(DOWN_IMAGE_TIME, System.currentTimeMillis())

                //文章列表顶部
                val topArticleListDeferred = async {
                    PlayAndroidNetWork.getTopArticleList()
                }
                val topArticleList = topArticleListDeferred.await()
                if (topArticleList.errorCode == 0 ) {//不刷新修改
                    res.addAll(topArticleList.data)
                    topArticleList.data.forEach {
                        it.localType = HOME_TOP
                    }
                }


                //文章列表Home页
                val articleListDeferred = async {
                    PlayAndroidNetWork.getArticleList(query.page - 1)
                }
                val articleList = articleListDeferred.await()
                if (articleList.errorCode == 0 ) {
                    res.addAll(articleList.data.datas)
                    articleList.data.datas.forEach {
                        it.localType = HOME
                    }
                    Result.success(res)
                } else {
                    Result.failure(
                        RuntimeException("response status is ${articleList.errorCode}" + "msg is ${articleList.errorMsg}")
                    )
                }

            } else {//如果 QueryHomeArticle中的page不是1
                val articleListDeferred = async {
                    PlayAndroidNetWork.getArticleList(query.page - 1)
                }
                val articleList = articleListDeferred.await()
                if (articleList.errorCode == 0) {
                    res.addAll(articleList.data.datas)
                    Log.e(TAG, "getArticleList: size:"+res.size )
                    Result.success(res)
                } else {
                    Log.e(TAG, "getArticleList:  articleList.errorCode.toString()")
                    Result.failure(
                        RuntimeException(
                            "response status is ${articleList.errorCode}" + "msg is ${articleList.errorMsg}"
                        )
                    )
                }
            }
        }

    }
}


const val ONE_DAY = 1000 * 60 * 60 * 24
const val FOUR_HOUR = 1000 * 60 * 60 * 4
const val DOWN_IMAGE_TIME = "DownImageTime"
const val DOWN_TOP_ARTICLE_TIME = "DownToArticleTime"
const val DOWN_ARTICLE_TIME = "DownArticleTime"
const val DOWN_PROJECT_ARTICLE_TIME = "DownProjectArticleTime"
const val DOWN_OFFICIAL_ARTICLE_TIME = "DownOfficialArticleTime"


//       coroutineScope {
//           val res=arrayListOf<Article>()
//           if (query.page==1){
//               val kv= MMKVUtils
//               var downArticleTime=0L
//               downArticleTime= MMKV.defaultMMKV().decodeLong(DOWN_IMAGE_TIME,System.currentTimeMillis())
//           }
//       }
