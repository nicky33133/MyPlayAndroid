package com.example.myplayandroid

import android.content.BroadcastReceiver
import android.os.Bundle
import android.view.View
import com.example.myplayandroid.article.collect.ArticleBroadCast
import com.example.myplayandroid.view.base.Ice.BaseFragment

//这个类的作用：在文章收藏状态发生变化时，自动刷新当前页面的数据
abstract class ArticleCollectBaseFragment : BaseFragment() {
    private var articleReceiver: BroadcastReceiver? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //注册广播接收器
        articleReceiver =
            ArticleBroadCast.setArticleChangesReceiver(requireActivity()) { refreshData() }
    }


    //这是一个 抽象方法，子类必须实现
    //作用是：每个子类都需要刷新数据，每个子类可以自定义自己刷新数据的具体实现
    //当然也可以不这样写，每个类自己写刷新数据；逻辑，不到那样会造成很多重复的代码
    //而且这样继承的方式，每个子类都可以直接拥有父类的广播功能
    abstract fun refreshData()

    override fun onResume() {//页面交互时调用
        super.onResume()
        refreshData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        //注销广播接收器
        //clearArticleChangesReceiver这个方法注销的
        ArticleBroadCast.clearArticleChangesReceiver(requireActivity(), articleReceiver)
    }
}