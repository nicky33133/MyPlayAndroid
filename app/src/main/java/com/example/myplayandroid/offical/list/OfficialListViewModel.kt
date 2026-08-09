package com.example.myplayandroid.offical.list

import androidx.lifecycle.LiveData
import com.example.core.view.base.lce.BaseAndroidViewModel
import com.example.model.pojo.QueryArticle
import com.example.model.room.entity.Article
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OfficialListViewModel @Inject constructor(
    private val officialRepository: OfficialRepository
) : BaseAndroidViewModel<List<Article>, Article, QueryArticle>() {

    override fun getData(page: QueryArticle): LiveData<Result<List<Article>>> {
        return officialRepository.getWxArticle(page)
    }

}