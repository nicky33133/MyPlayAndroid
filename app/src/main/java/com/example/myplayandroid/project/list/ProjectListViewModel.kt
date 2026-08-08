package com.example.myplayandroid.project.list

import androidx.lifecycle.LiveData
import com.example.core.view.base.lce.BaseAndroidViewModel
import com.example.model.pojo.QueryArticle
import com.example.model.room.entity.Article
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProjectListViewModel @Inject constructor(
    private val projectRepository: ProjectRepository
): BaseAndroidViewModel<List<Article>, Article, QueryArticle>() {

    override fun getData(page: QueryArticle): LiveData<Result<List<Article>>> {
        //getData与projectRepository.getProject(page)仓库的这个方法有关
        return projectRepository.getProject(page)
        //getProject方法 是获取项目具体文章列表
    }


}