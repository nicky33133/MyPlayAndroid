package com.example.model.room

import androidx.room.Database
import androidx.room.RoomDatabase
//import com.example.model.room.entity.Almanac
import com.example.model.room.entity.Article
import com.example.model.room.entity.BannerBean
import com.example.model.room.entity.HotKey
import com.example.model.room.entity.ProjectClassify

//作用是：作为 App 本地数据库的总管理器，负责创建数据库实例、管理数据表（Entity）版本、
// 提供数据访问对象（DAO），并处理数据库升级时的表结构变更
//@Database(
//    entities = [ProjectClassify::class, Article::class, HotKey::class, BannerBean::class, Almanac::class],
//    version = 2 //?
//)
//abstract class PlayDatabase: RoomDatabase(){
//    abstract fun projectClassifyDao():
//}