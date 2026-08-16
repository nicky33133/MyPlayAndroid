package com.example.myplayandroid.profile.rank.list

import androidx.lifecycle.LiveData
import com.example.core.view.base.lce.BaseAndroidViewModel
import com.example.model.model.Rank
import com.example.model.model.RankData
import com.example.myplayandroid.profile.rank.RankRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class RankViewModel @Inject constructor(
    private val rankRepository: RankRepository
):BaseAndroidViewModel<RankData, Rank, Int>() {

    override fun getData(page: Int): LiveData<Result<RankData>> {//返回数据类型
        return rankRepository.getRankList(page)
    }
}