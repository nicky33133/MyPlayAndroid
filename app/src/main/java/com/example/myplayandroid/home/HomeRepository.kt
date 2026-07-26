package com.example.myplayandroid.home

import android.app.Application
import com.example.core.util.MMKVUtils
import com.example.myplayandroid.base.liveDataFire
import com.tencent.mmkv.MMKV
//import dagger.hilt.android.scopes.ActivityRetainedScoped
//import javax.inject.Inject
import kotlin.contracts.contract


//@ActivityRetainedScoped
//@ActivityRetainedScoped：这是 Hilt 的作用域注解，
//表示该 Repository 的生命周期与 Activity 保持一致，并且在配置变更（如旋转屏幕）时不会重新创建
//class HomeRepository @Inject constructor(
    //@Inject constructor：通过构造器注入，
//由 Hilt 提供 Application 实例，方便获取全局上下文
//    val application: Application
//){
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
//}






const val ONE_DAY=1000*60*60*24
const val FOUR_HOUR=1000*60*60*4
const val DOWN_IMAGE_TIME="DownImageTime"
const val DOWN_TOP_ARTICLE_TIME="DownToArticleTime"
const val DOWN_ARTICLE_TIME="DownArticleTime"
const val DOWN_PROJECT_ARTICLE_TIME="DownProjectArticleTime"
const val DOWN_OFFICIAL_ARTICLE_TIME="DownOfficialArticleTime"





