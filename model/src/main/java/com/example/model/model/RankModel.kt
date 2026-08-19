package com.example.model.model



data class RankData(
    val curPage: Int,
    val datas: List<Rank>,
    val offset: Int,
    val over: Boolean,
    val pageCount: Int,
    val size: Int,
    val total: Int
)




data class Rank(
    //硬币数量
    val coinCount: Int,
    val level: Int,
    //. 等级；队列
    val rank: String,
    //拼写错误，导致系统找不到
//    val useId: Int,
    val userId: Int,

    val username: String
)