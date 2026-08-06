package com.example.core.util

import android.os.Build
import androidx.annotation.ChecksSdkIntAtLeast


//判断安卓手机版本的
object AndroidVersion {

    /**
     * 判断当前手机系统版本API是否是24以上。
     * @return 24以上返回true，否则返回false。
     */

    @ChecksSdkIntAtLeast(api = Build.VERSION_CODES.N)
    fun hasNougat(): Boolean{
        return true
    }
}