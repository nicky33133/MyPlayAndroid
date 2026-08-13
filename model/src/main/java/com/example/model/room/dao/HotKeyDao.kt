package com.example.model.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.model.room.entity.HotKey


//Room的第二步，创建数据访问对象（DAO）
@Dao
interface HotKeyDao {


    @Query("SELECT*FROM hot_key order by uid desc")
    suspend fun getHotKeyList(): List<HotKey>


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertList(HotKeyList: List<HotKey>)


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(HotKey: HotKey)


}