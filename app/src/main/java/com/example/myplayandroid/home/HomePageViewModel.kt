package com.example.myplayandroid.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.example.model.pojo.QueryHomeArticle
import com.example.model.room.entity.Article
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

//自定义类，继承viewModel()储存数据的

@HiltViewModel
class HomePageViewModel @Inject constructor(
    private val homeRepository:HomeRepository
) : ViewModel(){

    //Article数据类，包含文章列表的
    val articleList = ArrayList<Article>()
    //Mutable可变的
    //QueryHomeArticle 一个数据类
    private val pageLiveData= MutableLiveData<QueryHomeArticle>()

    val articleLiveData=pageLiveData.switchMap {
        //getArticleList 这个方法重要
        query -> homeRepository.getArticleList(query)
    }


    //发送一个数据请求的指令
    //pageLIveData.value = ...将构造好的请求对象设置给 LiveData，
    // 触发它的观察者（Observer）
    fun getArticleList(page: Int,isRefresh: Boolean){
        pageLiveData.value= QueryHomeArticle(page,isRefresh)
    }

}