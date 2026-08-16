package com.example.network.base

import com.example.network.service.HomePageService
import com.example.network.service.OfficialService
import com.example.network.service.ProjectService
import com.example.network.service.RankService
import com.example.network.service.ShareService
import kotlin.jvm.java

object PlayAndroidNetWork {

    //创建一个 HomePageService 接口的动态代理实例
    //自定义的单例类ServiceCreator
    //调用 ServiceCreator 对象的 create 方法
    //方法内部封装了 Retrofit 实例的构建 和 接口代理的生成
    private val homePageService = ServiceCreator.create(HomePageService::class.java)

    //封装
    suspend fun getTopArticleList() = homePageService.getTopArticle()
    suspend fun getArticleList(page: Int) = homePageService.getArticle(page)

    //首页搜索的
    suspend fun getHotKey() = homePageService.getHotKey()
    suspend fun getQueryArticleList(page: Int, k: String) =
        homePageService.getQueryArticleList(page, k)

//    private val collectService = ServiceCreator.create(CollectService::class.java)
//    suspend fun getCollectList(page: Int) = collectService.getCollectList(page)



    //创建一个ProjectService接口的动态代理实例
    //自定义的单例类ServiceCreator
    //调用 ServiceCreator 对象的 create 方法
    //方法内部封装了 Retrofit 实例的构建 和 接口代理的生成
    private val projectService= ServiceCreator.create(ProjectService::class.java)// ProjectService是接口，写了相对路劲的
    suspend fun getProjectTree() = projectService.getProjectTree()
    //这个getProject是一个封装，实际是projectService.getProject(page,cid)中的方法
    suspend fun getProject(page:Int,cid: Int)=projectService.getProject(page,cid)


    private val officialService= ServiceCreator.create(OfficialService::class.java)
    suspend fun getWxArticle(page: Int,cid: Int)=officialService.getWxArticle(page,cid)
    suspend fun getWxArticleTree()=officialService.getWxArticleTree()




    private val rankService= ServiceCreator.create(RankService::class.java)
    //排行列表
    suspend fun getRankList(page: Int)=rankService.getRankList(page)//getRankList是接口
    suspend fun getUserRank(page: Int)= rankService.getUserRank(page)
    suspend fun getUserInfo()=rankService.getUserInfo()



    //分享界面
    private val shareService= ServiceCreator.create(ShareService::class.java)
    suspend fun getMyShareList(page: Int)=shareService.getMyShareList(page)
    suspend fun getShareList(cid: Int,page: Int)=shareService.getShareList(cid,page)





}