package com.example.core.util

import android.content.Context
import android.widget.Toast

private var toast: Toast?=null

//主线程限定 + 空安全 + 单例复用的 Toast 封装
fun Context?.showToast(context: String?){
    if (Thread.currentThread().name != "main") return
    if (this == null) return

    //如果 toast 引用为空，则创建新 Toast；否则复用已存在的 Toast 对象
    if (toast == null ){
        toast = Toast.makeText(this,context, Toast.LENGTH_SHORT)
    }else{
        toast?.setText(context)
    }
    toast?.show()
}