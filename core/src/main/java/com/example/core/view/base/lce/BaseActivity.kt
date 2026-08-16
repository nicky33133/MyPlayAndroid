package com.example.core.view.base.lce

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import com.example.core.R
import com.example.core.util.showToast

//@SuppressLint("Registered")
abstract class BaseActivity: AppCompatActivity(), BaseActivityInit, ILce{
    override fun onCreate(
        savedInstanceState: Bundle?,
    ) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutView())
        // 把自己加入全局的 Activity 栈（方便一键退出等）
        initView()
        initData()
    }

    override fun initView() {}

    override fun initData() {}


    //网络请求和 UI 状态绑定在一起
    fun <T> setDataStatus(dataLiveData: LiveData<Result<T>>, onDataStatus: (T) -> Unit) {//两个参数
        //观测的结果（即 Result<T> 对象）来源于外部对 dataLiveData 的赋值操作。
        // 通常这个 LiveData 由 ViewModel 或 数据仓库层（Repository） 维护
        dataLiveData.observe(this) {
            if (it.isSuccess) { //it指代观测的结果result
                val dataList = it.getOrNull()
                if (dataList != null) {
                    loadFinished()// 隐藏所有遮罩，显示内容
                    onDataStatus(dataList) // 把数据丢给子类去更新列表/文本框
                } else {
                    showLoadErrorView() // 数据为空，显示“服务器错误”界面
                }
            } else {
                // 弹个 Toast
                showToast(getString(R.string.bad_network_view_tip))
                showBadNetworkView { initData() } // 显示“点我重试”，点了就重新请求
            }
        }
    }


    override fun startLoading() {}

    override fun loadFinished() {}

    override fun showLoadErrorView(tip: String) {}

    override fun showBadNetworkView(listener: View.OnClickListener) {}

    override fun showNoContentView(tip: String) {}
}