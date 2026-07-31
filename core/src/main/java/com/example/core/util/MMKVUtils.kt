package com.example.core.util
import android.content.Context
import com.tencent.mmkv.MMKV



//使用MMKV对数据进行操作，而不是DataStore
object MMKVUtils {
//    private lateinit var dataKv: MMKV
    //获取默认全局实例


    val mmkv = MMKV.defaultMMKV()


//    fun init(context: Context){
        //局初始化方法。它的作用是为 MMKV 框架准备好运行环境，
        // 必须在调用任何其他 MMKV API（如读写数据、创建实例）之前执行
//        MMKV.initialize(context)
//        dataKv= MMKV.defaultMMKV()
//    }

    //写入数据
    fun <U> putData(key: String,value:U){
        when(value){
            is Long-> mmkv.encode(key,value)
            is String -> mmkv.encode(key, value)
            is Int -> mmkv.encode(key, value)
            is Boolean -> mmkv.encode(key, value)
            is Float -> mmkv.encode(key, value)
            else -> throw IllegalArgumentException("This type can be saved into MMKV")
        }
    }

    //读取数据
    fun <U> getData(key: String,default:U):U{
        return when (default){
            //default是默认值，如果没有读到键值，就返回默认值
            is String ->mmkv.decodeString(key,default)
            is Int -> mmkv.decodeInt(key, default)
            is Long -> mmkv.decodeLong(key, default)
            is Float -> mmkv.decodeFloat(key, default)
            is Boolean -> mmkv.decodeBool(key, default)
            else -> throw IllegalArgumentException("不支持读取数据类型：${default!!::class.java}")
        }as U
    }

    //清楚所有数据
    fun clear(){
        mmkv.clearAll()
    }

}
