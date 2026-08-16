package com.example.model.model

import com.example.model.room.entity.Article

data class ShareModel (
    val coinInfo: CoinInfo,
    val shareArticles: ShareArticles
)

data class CoinInfo(
    val coinCount: Int,
    val level: Int,
    val rank: String,
    val userId: Int,
    val username: String
)


data class ShareArticles(
    val curPage: Int,
    val datas:List<Article>,//文章数据列表
    val offset: Int,
    val over: Boolean,
    val pageCount: Int,
    val size: Int,
    val total: Int
)