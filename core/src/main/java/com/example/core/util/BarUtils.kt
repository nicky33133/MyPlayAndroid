package com.example.core.util

import android.content.Context


//第二个碎片的顶部标题栏
//设置 TabLayout 的内边距（padding）的方法
fun Context?.getStatusBarHeight(): Int{
    //为 可空 Context（Context?）定义的扩展函数
    var result = 60 //设置一个默认值
    if (this == null) return result
    //因为接收者是 Context?，所以需要检查当前上下文是否为空
    val resId = resources.getIdentifier(
        "status_bar_height",
        "dimen",
        "android")
    //getIdentifier 是 Android 资源管理系统中的“反射式”查找方法
    //"status_bar_height"：资源名，这是 Android 系统内部定义的标准状态栏高度名称。
    //"dimen"：资源类型，表示这是一个尺寸资源（dimens.xml）。
    //"android"：包名


    if (resId > 0){
        //如果找到了 ID，调用 getDimensionPixelOffset(resId) 获取实际的像素值
        result = resources.getDimensionPixelOffset(resId)
    }

    return result
}