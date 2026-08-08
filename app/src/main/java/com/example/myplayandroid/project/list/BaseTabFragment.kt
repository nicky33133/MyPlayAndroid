package com.example.myplayandroid.project.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import com.example.myplayandroid.BaseFragment
import com.google.android.material.tabs.TabLayout

abstract class BaseTabFragment: BaseFragment(), ViewPager.OnPageChangeListener, TabLayout.OnTabSelectedListener {
    //继承BaseFragment：获得了LCE（加载/内容/错误）状态管理能力
//任何 Tab 页面都能轻松处理数据加载状态

    //监听旧版 ViewPager
    //监听 TabLayout 的 Tab 选中事件

    override fun onPageScrolled(
        position: Int,
        positionOffset: Float,
        positionOffsetPixels: Int
    ) {

    }

    override fun onPageSelected(position: Int) {
        onTabPageSelected(position)
    }

    override fun onPageScrollStateChanged(state: Int) {

    }

    override fun onTabSelected(tab: TabLayout.Tab?) {
        if (tab != null && tab.position > 0)
        //将位置传递给子类
            onTabPageSelected(tab.position)
    }

    override fun onTabUnselected(p0: TabLayout.Tab?) {

    }

    override fun onTabReselected(p0: TabLayout.Tab?) {

    }

    //抽象方法，子类必须实现。
    abstract fun onTabPageSelected(position: Int)
}