package com.example.core.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log

//检查网络可用
fun Context?.checkNetworkAvailable(): Boolean{

     // 如果 this 是 null，直接告诉外界‘是的，它确实是空的’（返回 true）
    if (this == null) return true

    //网络连接管理服务（ConnectivityManager）
    val connectivityManager=
        //      getSystemService系统提供的一个服务获取方法
        //Context.CONNECTIVITY_SERVICE这是一个常量字符串，它的值实际上是 "connectivity"
        getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?

    val network=connectivityManager?.activeNetwork
    return if (network == null){
        Log.w("checkNetworkAvailable", "checkNetworkAvailable: Now no network")
        false
    }else{
        //getNetworkCapabilities()	管家提供的一个方法，意思是：“请把某个网络的能力详情报告给我
        //类型是 NetworkCapabilities（网络能力）。它包含了该网络的所有特征信息
        val networkCapabilities=connectivityManager.getNetworkCapabilities(network)

        //蜂窝网络
        //!= false 的意思是 “只要结果不是明确的 false，我就认为是 true
        if (networkCapabilities?.hasTransport(
                NetworkCapabilities.TRANSPORT_CELLULAR) != false
            ){
            Log.w("checkNetworkAvailable", "checkNetworkAvailable: Now is cellular")
        }
        // Wi-Fi
        if (networkCapabilities?.hasTransport(
                NetworkCapabilities.TRANSPORT_WIFI) != false
            ){
            Log.w("checkNetworkAvailable", "checkNetworkAvailable: Now is WIFI")
        }
        true
    }
}