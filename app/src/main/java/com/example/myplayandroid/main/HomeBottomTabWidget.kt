package com.example.myplayandroid.main

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.example.myplayandroid.R
import com.example.myplayandroid.databinding.LayoutHomeBottomTabBinding

class HomeBottomTabWidget @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : BaseHomeBottomTabWidget(context, attrs, defStyleAttr),
    View.OnClickListener {
    private var textViews = ArrayList<TextView>()
    init {
        //加载视图，有四个关键按钮的
        val view= LayoutHomeBottomTabBinding.inflate(LayoutInflater.from(context), this, true)
        view.apply {
            textViews=arrayListOf(llHomeATHome,llHomeATCalendar,llHomeATObject,llHomeATMy)
        }
        for (textView in textViews){
            textView.setOnClickListener(this)

        }
    }

    //销毁，避免内存泄漏
    override fun destroy() {
        super.destroy()
        if (textViews.isNotEmpty()){
            textViews.clear()
        }
    }
    //实现按钮的点击事件
    override fun onClick(v: View?) {
        when(v?.id){
            R.id.llHomeATHome->fragmentManger(0)
            R.id.llHomeATCalendar->fragmentManger(1)
            R.id.llHomeATObject->fragmentManger(2)
            R.id.llHomeATMy->fragmentManger(3)
        }
    }

    //fragment的切换 实现底部导航栏的切换
    override fun fragmentManger(position: Int) {
        super.fragmentManger(position)//1. 先执行父类逻辑（切换Fragment）
        for (j in textViews.indices){  // ② 遍历底部导航栏的 TextView 列表
            textViews[j].isSelected=position==j // ③ 根据位置设置选中状态
        }
    }
}