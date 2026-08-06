package com.example.network.base

import com.example.network.service.HomePageService

object PlayAndroidNetWork {

    //创建一个 HomePageService 接口的动态代理实例
    //自定义的单例类ServiceCreator
    //调用 ServiceCreator 对象的 create 方法
    //方法内部封装了 Retrofit 实例的构建 和 接口代理的生成
    private val homePageService = ServiceCreator.create(HomePageService::class.java)

    //封装
    suspend fun getTopArticleList() = homePageService.getTopArticle()
    suspend fun getArticleList(page: Int) = homePageService.getArticle(page)
    suspend fun getHotKey() = homePageService.getHotKey()
    suspend fun getQueryArticleList(page: Int, k: String) =
        homePageService.getQueryArticleList(page, k)

//    private val collectService = ServiceCreator.create(CollectService::class.java)
//    suspend fun getCollectList(page: Int) = collectService.getCollectList(page)

}