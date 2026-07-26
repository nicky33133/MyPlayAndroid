package com.example.model.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


//负责存储分类的元数据
// Room 数据库的实体类（Entity），对应的是“项目分类（Project Classification）”表。
// 它的作用是 “缓存玩安卓 API 中‘项目’模块的顶级分类列表，用于构建项目页面的 Tab 标签栏”。
@Entity(tableName="project_classify")
data class ProjectClassify(
    @PrimaryKey(autoGenerate = true)val uid: Int,
    @ColumnInfo(name = "course_id")val courseId: Int,
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "order_classify") val order: Int,
    @ColumnInfo(name = "parent_chapter_id") val parentChapterId: Int,
    @ColumnInfo(name = "user_control_set_top") val userControlSetTop: Boolean,
    @ColumnInfo(name = "visible") val visible: Int
)
