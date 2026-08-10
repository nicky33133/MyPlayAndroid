package com.example.model.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.model.room.entity.ProjectClassify

// Room 数据库的数据访问对象（DAO）接口
//用于操作 project_classify 表
//为上层（Repository）提供数据的增删改查能力(“项目分类”和“公众号分类")

@Dao
interface ProjectClassifyDao {
    @Query("SELEct*FROM project_classify where order_classify>144999 and order_classify<145050")
    suspend fun getAllProject(): List<ProjectClassify>

    @Query("SELECT*FROM project_classify where order_classify>18999 and order_classify<190020")
    //order_classify 字段的值必须同时满足大于 189999 且小于 190020
    suspend fun getAllOfficial(): List<ProjectClassify>



    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertList(projectClassifyList: List<ProjectClassify>)


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(projectClassify: ProjectClassify)


    @Delete
    suspend fun delete(projectClassify: ProjectClassify): Int


    @Delete
    suspend fun deleteList(projectClassifyList: List<ProjectClassify>): Int


    @Query("DELETE FROM project_classify")
    suspend fun deleteAll()

    }