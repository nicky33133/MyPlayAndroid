package com.example.model.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.model.room.dao.BrowseHistoryDao
import com.example.model.room.dao.HotKeyDao
import com.example.model.room.dao.ProjectClassifyDao
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


//Room 使用第三步， 构建数据库（Database）
//定义好3个部分的内容：数据库的版本号、包含哪些实体类，以及提供Dao层的访问实例
@Database(
    entities = [ProjectClassify::class, Article::class, HotKey::class],
    version = 2 //从2改成了1
)//记录点：  新增加了HotKey数据库，但是这里没有加上HotKey::class

abstract class PlayDatabase: RoomDatabase(){

    abstract fun projectClassifyDao(): ProjectClassifyDao

    abstract fun browseHistoryDao(): BrowseHistoryDao

    //首页搜索功能的
    abstract fun hotKeyDao(): HotKeyDao

    companion object{
        @Volatile
        //PlayDatabase抽象类的实例
        private var INSTANCE: PlayDatabase?=null

        //该方法返回instance
        fun getDatabase(context: Context): PlayDatabase{
            //INSTANCE赋值给tempInstance
            val temInstance=INSTANCE
            if (temInstance != null){
                return temInstance
            }

            // 线程锁,同一时刻只有一个线程能进入这个代码块
            synchronized(this){
              //Room.databaseBuilder(...),这是 Room 提供的构建器工厂方法
                val instance=Room.databaseBuilder(
                    context.applicationContext,
                    PlayDatabase::class.java,
                    "play_database"
                    //版本迁移
                ).addMigrations(MIGRATION_1_2).build()
                // INSTANCE赋值
                INSTANCE=instance
                return instance
            }
        }
    }

}


//一个 Migration 对象，
// 里面包含从版本 1 到版本 2 的 SQL 升级脚本

////object :：这是 Kotlin 的匿名对象,它直接创建了一个 Migration 抽象类的实例。
val MIGRATION_1_2: Migration=object : Migration(1,2){

    override fun migrate(db: SupportSQLiteDatabase) {

        // SQLite 建表语句
        //创建新表connect_prod并添加对应的字段
        //PRIMARY KEY(id)将id设置为主键，NOT NULL设置对应的键不能为空
        db.execSQL(
            "CREATE TABLE IF NOT EXISTS `banner_bean` (`uid` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    "`desc` TEXT NOT NULL, `id` INTEGER NOT NULL, `imagePath` TEXT NOT NULL, `isVisible` INTEGER NOT NULL, " +
                    "`order` INTEGER NOT NULL, `title` TEXT NOT NULL, `type` INTEGER NOT NULL, `url` TEXT NOT NULL, `file_path` TEXT NOT NULL)"
        )
    }
}