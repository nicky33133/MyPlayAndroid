package com.example.network.service

import com.example.model.model.ArticleList
import com.example.model.model.BaseModel
import com.example.model.room.entity.ProjectClassify
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


//基于OkHttp的网络封装框架
//相对路径
interface ProjectService {

    @GET("project/tree/json")
    suspend fun getProjectTree(): BaseModel<List<ProjectClassify>>


    //相对路径
    @GET("project/list/{page}/json")
    suspend fun getProject(@Path("page") page: Int,@Query("cid") cid: Int): BaseModel<ArticleList>
}