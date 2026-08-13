package com.example.myplayandroid.home.article

import androidx.lifecycle.LiveData
import com.example.core.view.base.lce.BaseAndroidViewModel
import com.example.model.model.ArticleList
import com.example.model.pojo.QueryArticle
import com.example.model.room.entity.Article
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


//搜索以后内容展示的
@HiltViewModel
class ArticleListViewModel @Inject constructor(
    private val searchRepository: SearchRepository
): BaseAndroidViewModel<ArticleList, Article, QueryKeyArticle>(){
    override fun getData(page: QueryKeyArticle): LiveData<Result<ArticleList>> {
      return searchRepository.getQueryArticleList(page.page,page.k)
    }
}

data class QueryKeyArticle(var page:Int,var k: String)