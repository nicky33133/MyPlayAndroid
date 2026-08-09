package com.example.network.service

import com.example.model.model.ArticleList
import com.example.model.model.BaseModel
import com.example.model.room.entity.ProjectClassify
import retrofit2.http.GET
import retrofit2.http.Path

interface OfficialService {

    @GET("wxarticle/chapters/json")
    suspend fun getWxArticleTree(): BaseModel<List<ProjectClassify>>

    @GET("wxarticle/list/{cid}/{page}/json")
    suspend fun getWxArticle(
        //这两个的顺序要与上面一一对应,"cid","page"要写正确
        @Path("cid") cid: Int,
        @Path("page") page: Int
    ): BaseModel<ArticleList>
}