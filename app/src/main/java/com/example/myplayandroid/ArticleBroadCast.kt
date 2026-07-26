package com.example.myplayandroid

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager


//广播工具类
//作用：发送，注册。注销广播
object ArticleBroadCast {
    //定义广播的 Action 字符串，作为广播的唯一标识。所有发送和接收都必须使用相同的 Action
    const val COLLECT_RECEIVER="com.zj.play.COLLECT"

    //发送广播
    fun  sendArticleChangesReceiver(context: Context) {
        //指定 Action 为 COLLECT_RECEIVER
        val intent= Intent(COLLECT_RECEIVER)
        //发送本地广播，用 LocalBroadcastManager（本地广播）
        //这是全局广播（sendBroadcast）
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent)
    }


    //注册广播接收器
    fun setArticleChangesReceiver(c: Activity, block:()-> Unit) : BroadcastReceiver{
        val filter= IntentFilter()
        filter.addAction(COLLECT_RECEIVER)
        val r=ArticleBroadcastReceiver(block)
        //注册
        LocalBroadcastManager.getInstance(c).registerReceiver(r,filter)
        return r//返回值：BroadcastReceiver 实例
    }

    //作用：注销广播
    fun clearArticleChangesReceiver(c: Activity, r: BroadcastReceiver?){
        r?.apply {//如果 r 不为空，执行下面的代码
            //注销
            LocalBroadcastManager.getInstance(c).unregisterReceiver(this)
        }
    }

    //一个私有类
    //作用是注册广播时调用
    private class ArticleBroadcastReceiver(val block:()-> Unit): BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
          Log.e("TAG","onReceive:${intent?.action}")
            //检查收到的 Intent 的 Action 是否匹配 COLLECT_RECEIVER
            if (intent?.action== ArticleBroadCast.COLLECT_RECEIVER){
                block.invoke()
            }
        }
    }
}