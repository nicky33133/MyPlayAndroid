package com.example.myplayandroid

import android.content.Context
import android.widget.Toast

private var toast: Toast?=null

//第一个重载函数：接收字符串
fun Context?.showToast(context: String?){
    //Thread.currentThread()：获取当前正在执行这段代码的线程对象
    //这里通过线程名粗暴地拦截子线程调用
    if (Thread.currentThread().name!="main")return
    //接收者空检查
    if (this==null)return
    //判断缓存是否为空，检查顶层的 toast 变量是否为空
    if (toast==null){
        toast= Toast.makeText(//如果是空的，就创建新的 Toast 实例
            this,
            context,
            Toast.LENGTH_SHORT
        )
    }else{
        toast?.setText(context)//如果不是空的，更新已有 Toast 的文本
    }
    toast?.show()//显示 Toast
}

//第二个重载函数：接收资源 ID
fun Context?.showToast(resId: Int){
    if (this==null)return
    if (toast==null){
        toast= Toast.makeText(
            this,
            resId,
            Toast.LENGTH_SHORT
        )
    }else{
        toast?.setText(resId)
    }
    toast?.show()
}