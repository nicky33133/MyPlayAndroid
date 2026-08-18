package com.example.myplayandroid.profile.history

import android.app.Application
import com.example.model.room.PlayDatabase
import com.example.model.room.entity.HISTORY
import com.example.myplayandroid.base.liveDataFire
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class BrowseHistoryRepository @Inject constructor(val application: Application){

    //传入 Application 而不是 Activity 或 Fragment 的 Context，是因为：
    //传入类型	生命周期	内存泄漏风险
    //Activity	跟随 Activity	高 — Repository 生命周期比 Activity 长，持有 Activity 引用会导致泄漏
    //Application	跟随应用进程	无 — Application 本身就是单例，生命周期和应用一致

    private val browseHistoryDao= PlayDatabase.getDatabase(application).browseHistoryDao()


    fun getBrowseHistory(page: Int)= liveDataFire {
        val projectClassifyLists=browseHistoryDao.getHistoryArticleList((page - 1)*20, HISTORY)

        if (projectClassifyLists.isNotEmpty()){
            Result.success(projectClassifyLists)
        }else{
            Result.failure(RuntimeException("response status is "))
        }
    }
}