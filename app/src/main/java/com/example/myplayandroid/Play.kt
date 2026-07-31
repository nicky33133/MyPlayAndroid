package com.example.myplayandroid

import android.content.Context
import android.util.Log
import com.example.core.util.MMKVUtils
import com.example.myplayandroid.databinding.FragmentProfileBinding
import com.tencent.mmkv.MMKV
import kotlinx.coroutines.flow.Flow

object Play {
    var context: Context?=null
    private const val TAG="Play"
//    private lateinit var dataKv: MMKVUtils
    private const val NICE_NAME="nickname"//绰号，昵称
    private const val USERNAME="username"
    //const（编译时常量修饰符）
    //这是最关键的修饰符，表示该值在编译期就确定了。编译器会把所有使用 IS_LOGIN 的地方直接替换成字面量 "isLogin"
    private const val IS_LOGIN="isLogin"
//    private const val IS_LOGIN="isLogin"
//    private const val IS_LOGIN="isLogin"
//    private const val IS_LOGIN="isLogin"
//    private const val IS_LOGIN="isLogin"

//    fun initialize(c: Context){
////        if (c==null){
//            Log.w(TAG,"initialize:context is null")
////            return
//        }
//        context=c
//        context?.apply {
            //1. 初始化 MMKV，传入当前 Context
            //这里使用MMKV框架
//            val dataKv: String? = MMKV.initialize(this)
//             dataKv=MMKVUtils.init(c)

//        MMKVUtils.init(c)
//    }


      //判断用户是否已登录。
     //已登录返回true，未登录返回false。
     fun isLogin(): Boolean{
         return MMKVUtils.getData(IS_LOGIN,false)
     }

    val nickname: String//val 只保证“引用不可变”。它不保证“返回值不变”。
        //自定义了 get()，每次调用都去读 MMKV，只要外部调用
        get() = MMKVUtils.getData(NICE_NAME,"")
    val username: String
    get() = MMKVUtils.getData(USERNAME,"")
    //等价于：这里用java语言举例
    //private String getNickname() {
    // return MMKVUtils.getData("NICE_NAME", "");
    // }
    // 使用时：getNickname()

}