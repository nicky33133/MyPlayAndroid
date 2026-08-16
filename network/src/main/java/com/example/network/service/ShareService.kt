package com.example.network.service

import com.example.model.model.BaseModel
import com.example.model.model.ShareModel
import retrofit2.http.GET
import retrofit2.http.Path

interface ShareService {

//    @GET("user/{cid}/share_articles/{page}/json")
//    @GET("user/{cid}/share_articles/{page}/json")
//    suspend fun getShareList(@Path("cid") cid: Int, @Path("page") page: Int): BaseModel<ShareModel>
    @GET("user/{cid}/share_articles/{page}/json")
    suspend fun getShareList(@Path("cid") cid: Int, @Path("page") page: Int): BaseModel<ShareModel>
//    suspend fun getShareList(@Path("cid") cid: Int,@Path("path") page: Int): BaseModel<ShareModel>


    @GET("user/lg/private_articles/{page}/json")
    suspend fun getMyShareList(@Path("page") page: Int): BaseModel<ShareModel>


}