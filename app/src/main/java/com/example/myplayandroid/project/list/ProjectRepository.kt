package com.example.myplayandroid.project.list

import android.app.Application
import com.example.model.pojo.QueryArticle
import com.example.model.room.entity.PROJECT
import com.example.myplayandroid.base.liveDataFire
import com.example.network.base.PlayAndroidNetWork
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject


//项目页第二个碎片的仓库
@ActivityRetainedScoped
class ProjectRepository  @Inject constructor(
    val application: Application
){

    //获取项目标题列表
    fun getProjectTree(isRefresh: Boolean)= liveDataFire {
        val projectTree= PlayAndroidNetWork.getProjectTree()
        if (projectTree.errorCode == 0){
            val projectList=projectTree.data
            Result.success(projectList)
        }else{
            Result.failure(RuntimeException("response status is ${projectTree.errorCode}  msg is ${projectTree.errorMsg}"))
        }
    }



    //获取项目具体文章列表
    // @param query 查询类
    //getProject仓库的这个方法在进行拿数据的操作
    fun getProject(query: QueryArticle)=liveDataFire {
        if (query.page == 1){
            //在这里调用网络
            //PlayAndroidNetWork.getProject()调用网络单例类的getProject()方法
            val projectTree = PlayAndroidNetWork.getProject(query.page,query.cid)
            if (projectTree.errorCode == 0){
                projectTree.data.datas.forEach {
                    it.localType = PROJECT
                }
                Result.success(projectTree.data.datas)

            }else{
                Result.failure(RuntimeException("response status is  ${projectTree.errorCode}  msg is ${projectTree.errorMsg}"))
            }
        }else{
            val projectTree = PlayAndroidNetWork.getProject(query.page,query.cid)
            if (projectTree.errorCode == 0){
                Result.success(projectTree.data.datas)
            }else{
                Result.failure(RuntimeException("response status is ${projectTree.errorCode}  msg is ${projectTree.errorMsg}"))
            }
        }
    }
}