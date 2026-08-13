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

}
//getArticleListForChapterId
//deleteAll
//insertList