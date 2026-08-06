package com.example.core.util

import android.text.Html

fun getHtmlText(text: String): String{
    return if (AndroidVersion.hasNougat()){
        //若运行在 Android 7.0（API 24）及以上，
        // 使用带 FROM_HTML_MODE_LEGACY 参数的 Html.fromHtml() 解析
        Html.fromHtml(text, Html.FROM_HTML_MODE_LEGACY).toString()
    }else{
        text
    }
}