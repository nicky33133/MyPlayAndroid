package com.example.model.room.dao

import androidx.room.Query
import com.example.model.room.entity.ProjectClassify

// Room 数据库的数据访问对象（DAO）接口
//用于操作 project_classify 表
//为上层（Repository）提供数据的增删改查能力(“项目分类”和“公众号分类")
interface ProjectClassifyDao {
    @Query("SELEct*FROM project_classify where order_classify>144999 and order_classify<145050")
    suspend fun getAllProject(): List<ProjectClassify>
    }