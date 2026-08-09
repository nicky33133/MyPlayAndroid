package com.example.myplayandroid.offical.list

import androidx.lifecycle.LiveData
import com.example.core.view.base.lce.BaseAndroidViewModel
import com.example.model.pojo.QueryArticle
import com.example.model.room.entity.Article
import com.example.model.room.entity.ProjectClassify
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class OfficialViewModel @Inject constructor(
    private val officialRepository: OfficialRepository
    //ProjectClassify是一个数据类
): BaseAndroidViewModel<List<ProjectClassify>, Unit, Boolean>() {
    //为什么要List<ProjectClassify> ?
    //等会

    //在 Kotlin 中，Unit 是一个类型，同时它也是这个类型的唯一实例（单例）。
    // 它的核心含义是：“这个操作完成了，但没有任何有意义的数据要返回”。

//    var position=0
//    override fun getData(page: Boolean): LiveData<Result<List<Article>>> {
//        return officialRepository.getWxArticle(page)
//    }


    override fun getData(page: Boolean): LiveData<Result<List<ProjectClassify>>> {
        return officialRepository.getWxArticleTree(page)
    }

    //！！重要，getDataList执行， pageLiveData的值才会改变
    init {
        getDataList(false)
    }

    //  fun getDataList(page: Key){
    //        //给pageLiveData设置值
    //        pageLiveData.value=page!!
    //    }
}