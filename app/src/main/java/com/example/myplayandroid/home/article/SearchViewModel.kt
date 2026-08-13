package com.example.myplayandroid.home.article

import androidx.lifecycle.LiveData
import com.example.core.view.base.lce.BaseAndroidViewModel
import com.example.model.room.entity.HotKey
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchRepository: SearchRepository
): BaseAndroidViewModel<List<HotKey>, HotKey, Boolean>() {
    override fun getData(page: Boolean): LiveData<Result<List<HotKey>>> {
        return searchRepository.getHotKey()
    }

}