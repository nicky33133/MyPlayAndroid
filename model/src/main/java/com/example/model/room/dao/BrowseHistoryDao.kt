package com.example.model.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.model.room.entity.Article


@Dao
interface BrowseHistoryDao {
    @Query("SELECT*FROM browse_history")
    suspend fun getAllArticle(): List<Article>


    //where id = :id and local_type = :type：查询条件。
    //必须同时满足 id 匹配 且 local_type 匹配。
    //注意：这里的 :id 和 :type 是占位符，它们会对应到方法参数的变量名
    @Query("SELECT*FROM browse_history where id = :id and local_type = :type")
    suspend fun getArticle(id: Int,type: Int): Article?




    @Query("SELECT*FROM browse_history where local_type = :type and chapter_id = :chapterId")
    //上面的type，chapterId，需要下面的方法，确认这两个是参数
    suspend fun getArticleListForChapterId(type: Int, chapterId: Int): List<Article>


    @Query("DELETE FROM browse_history where local_type = :type and  chapter_id = :chapterId")
    suspend fun deleteAll(type: Int, chapterId: Int)


    //IGNORE	忽略新数据
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertList(articleList: List<Article>)


    //REPLACE	删除旧行，插入新行
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(article: Article)

    //浏览历史
    @Query(
        "SELECT*FROM browse_history " +
                "where local_type = :type " +
                "order by uid desc " +
                "limit :page , 20")

    //"SELECT*FROM browse_history "
    //指定数据源  //* 表示返回 browse_history 表中的所有列（字段）

    // "where local_type = :type " 条件过滤
    //local_type	数据库表中的列名，通常表示内容类型（如 1=文章，2=视频，3=图片）
    //=	等于比较运算符
    //:type	参数绑定 — 对应 Kotlin 函数参数 type: Int


    //  "order by uid desc " 排序规则
    //uid	按主键 uid 列排序（通常是自增主键，越大表示越新插入）
    //DESC	降序排列（从大到小），即最新的记录排在前面


    //  "limit :page , 20")
    //:page	第一个参数：偏移量（offset） — 跳过多少条记录
    //20	第二个参数：限制数量（limit） — 最多返回多少条
    suspend fun getHistoryArticleList(page: Int,type: Int): List<Article>


}
//getArticleListForChapterId
//deleteAll
//insertList