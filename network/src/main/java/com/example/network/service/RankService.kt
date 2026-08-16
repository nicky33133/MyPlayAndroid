package com.example.network.service

import com.example.model.model.BaseModel
import com.example.model.model.Rank
import com.example.model.model.RankData
import com.example.model.model.RankList
import com.example.model.model.UserInfo
import retrofit2.http.GET
import retrofit2.http.Path

interface RankService {

    //相对路径
    @GET("coin/rank/{page}/json")
    suspend fun getRankList(@Path("page") page: Int): BaseModel<RankData>




    @GET("lg/coin/userinfo/json")
    suspend fun getUserInfo(): BaseModel<UserInfo>//返回的数据类型UserInfo


    @GET("lg/coin/list/{page}/json")
    suspend fun getUserRank(@Path("page") page: Int): BaseModel<RankList>
}