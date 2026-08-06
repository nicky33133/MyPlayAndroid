package com.example.model.model

import com.example.model.room.entity.Article

data class ArticleList(
    val curPage: Int,
    //Article包含浏览历史数据的数据类
    val datas: List<Article>,
    val offset: Int,
    val over: Boolean,
    val pageCount: Int,
    val size: Int,
    val total: Int

)