package com.example.myplayandroid

import android.view.View
import android.widget.TextView
import com.example.myplayandroid.view.base.Ice.ILce

//ILce接口的实现类
class DefaultLceImpl constructor (//构造函数
    private val loading: View?,
    private val loadErrorView: View?,
    private val badNetworkView: View?,
    private val noContentView:View?
    ): ILce{
    override fun startLoading() {
        loadFinished()
        loading?.visibility=View.VISIBLE
    }

    override fun loadFinished() {//隐藏所有状态视图
        loading?.visibility=View.GONE
        badNetworkView?.visibility=View.GONE
        noContentView?.visibility=View.GONE
        loadErrorView?.visibility=View.GONE
    }

    //显示“加载错误”视图，并设置错误提示文本
    override fun showLoadErrorView(tip: String) {
        loadFinished()
        val loadErrorText=loadErrorView?.findViewById<TextView>(R.id.loadErrorText)
        loadErrorText?.text=tip//设置参数值
        loadErrorView?.visibility= View.VISIBLE//可见
    }

    //显示“网络异常”视图，并为该视图设置点击监听器
    override fun showBadNetworkView(listener: View.OnClickListener) {
      loadFinished()
        badNetworkView?.visibility=View.VISIBLE
        badNetworkView?.setOnClickListener(listener)//设置监听器
    }

    //显示“数据为空”视图，并设置提示文本
    override fun showNoContentView(tip: String) {
        loadFinished()
        val noContentText=noContentView?.findViewById<TextView>(R.id.noContentText)
        noContentText?.text=tip
        noContentView?.visibility=View.VISIBLE
    }
}
