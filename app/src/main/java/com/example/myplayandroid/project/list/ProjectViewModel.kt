package com.example.myplayandroid.project.list

import androidx.lifecycle.LiveData
import com.example.core.view.base.lce.BaseAndroidViewModel
import com.example.model.room.entity.ProjectClassify
import com.example.myplayandroid.article.collect.CollectRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import java.security.Key
import javax.inject.Inject

//自定义ViewModel类
@HiltViewModel
class ProjectViewModel @Inject constructor(
    //参数projectRepository是仓库的实例
    //仓库有关数据
    private val projectRepository: ProjectRepository//仓库
): BaseAndroidViewModel<List<ProjectClassify>,Unit, Boolean>() {

    var position = 0

    override fun getData(page: Boolean): LiveData<Result<List<ProjectClassify>>> {
        return projectRepository.getProjectTree(page)
        //getProjectTree方法获取项目标题列表
    }

    init {
        getDataList(false)
    }

}