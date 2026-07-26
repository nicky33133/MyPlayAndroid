package com.example.myplayandroid

import android.content.Context
import android.util.Log
import com.tencent.mmkv.MMKV

object Play {
    var context: Context?=null
    private const val TAG="Play"
    fun initialize(c: Context){
        if (c==null){
            Log.w(TAG,"initialize:context is null")
            return
        }
        context=c
        context?.apply {
            //1. 初始化 MMKV，传入当前 Context
            val kv: String? = MMKV.initialize(this)
        }
    }

}