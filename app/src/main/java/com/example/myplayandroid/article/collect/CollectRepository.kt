package com.example.myplayandroid.article.collect

import com.example.myplayandroid.base.liveDataModel
import com.example.network.base.PlayAndroidNetWork
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton


//仓库层，
@Singleton
class CollectRepository @Inject constructor(){


//    fun getCollectList(page: Int) = liveDataModel { PlayAndroidNetWork.get}

}

@EntryPoint
@InstallIn(SingletonComponent::class)
interface CollectRepositoryPoint{
    fun collectRepository(): CollectRepository
}