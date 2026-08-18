package com.example.myplayandroid.profile.history

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.core.view.base.lce.BaseAndroidViewModel
import com.example.model.room.PlayDatabase
import com.example.model.room.entity.Article
import com.example.model.room.entity.HISTORY
import com.example.myplayandroid.base.liveDataFire
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

//@ActivityRetainedScoped 注解表示这个 Repository 会跨越配置变化（如屏幕旋转）存活，
// 但会随 Activity 销毁而清理。如果传入 Activity Context，
// 在屏幕旋转时旧 Activity 被销毁但 Repository 还持有其引用，就会泄漏

@HiltViewModel
class BrowseHistoryViewModel @Inject constructor(
    private val browseHistoryRepository: BrowseHistoryRepository
) : BaseAndroidViewModel<List<Article>, Article, Int>() {

    override fun getData(page: Int): LiveData<Result<List<Article>>> {
        return browseHistoryRepository.getBrowseHistory(page)
    }


}