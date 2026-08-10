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


    @Query("SELECT*FROM browse_history where local_type = :type and chapter_id = :chapterId")
    //上面的type，chapterId，需要下面的方法，确认这两个是参数
    suspend fun getArticleListForChapterId(type: Int, chapterId: Int): List<Article>


    @Query("DELETE FROM browse_history where local_type = :type and  chapter_id = :chapterId")
    suspend fun deleteAll(type: Int, chapterId: Int)


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertList(articleList: List<Article>)

}
//getArticleListForChapterId
//deleteAll
//insertList