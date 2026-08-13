package com.example.myplayandroid.article

import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import android.util.Log
import android.webkit.WebView

class X5WebView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet?=null,
    defStyleAttr: Int=0
): WebView(context,attrs,defStyleAttr){

    private var mTouchByUser = false //布尔类型的变量

    private  val TAG = "X5WebView"

    override fun loadUrl(url: String) {
        super.loadUrl(url)
        resetAllStateInternal(url)
    }


    // 加载url时重置touch状态
    //重置所有内部状态resetAllStateInternal
    private fun resetAllStateInternal(url: String){
        Log.w(TAG, "resetAllStateInternal: url:$url", )

        if (
            //TextUtils.isEmpty() 会同时判断 null 和空字符串 ""
        //url.startsWith("javascript:")	检查 url 是否以 javascript: 开头
            url != null && !TextUtils.isEmpty(url) && url.startsWith("javascript:")){
            return
        }
        // 加载url时重置touch状态
        resetAllState()
    }



    // 加载url时重置touch状态
    //?
    private fun resetAllState(){
        mTouchByUser=false
    }
}