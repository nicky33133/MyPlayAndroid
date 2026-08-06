package com.example.network.service

import com.example.model.model.ArticleList
import com.example.model.model.BaseModel
import com.example.model.room.entity.Article
import com.example.model.room.entity.HotKey
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface HomePageService {

    //Retrofit 网络请求接口中的一个方法声明
    //Retrofit 的 HTTP 方法注解，表示该请求使用 GET 方法
    //"article/top/json"：这是接口的 相对路径
    @GET("article/top/json")//置顶文章
    //suspend一个 挂起函数
    //BaseModel<T>：数据封装类
    //Article数据类
    suspend fun getTopArticle(): BaseModel<List<Article>>

    //文章列表，接口地址是动态变化的
    @GET("article/list/{a}/json")//{a}占位符
    //ArticleList数据类
    //比如：调用 getArticle(5) 时，Retrofit 会将路径中的 {a} 占位符替换为参数 a 的实际值（本例中为 5）
    suspend fun getArticle(@Path("a")a: Int): BaseModel<ArticleList>

    //Query疑问//带参数
    @POST("article/query/{page}/json")
    //传入两个参数，追加查询参数（@Query），拼接到路径末尾，格式为 ?key=value
    suspend fun getQueryArticleList(@Path("page")page: Int,@Query("k")k: String): BaseModel<ArticleList>

    //热点
    @GET("hotkey/json")
    suspend fun getHotKey(): BaseModel<List<HotKey>>
}