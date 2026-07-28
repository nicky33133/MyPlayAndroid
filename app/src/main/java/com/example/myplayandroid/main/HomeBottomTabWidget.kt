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
        //可变列表
    private var textViews = ArrayList<TextView>()

    init {
        //加载视图，有四个关键按钮的
        val view= LayoutHomeBottomTabBinding.inflate(LayoutInflater.from(context), this, true)
        view.apply {
            textViews=arrayListOf(llHomeATHome,llHomeATCalendar,llHomeATObject,llHomeATMy)
        }
        for (textView in textViews){
            textView.setOnClickListener(this)//给四个按钮设置点击监听
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
    //先切换，后高亮
    override fun fragmentManger(position: Int) {
        super.fragmentManger(position)//1. 先执行父类逻辑（切换Fragment）
        //遍历底部导航栏的 TextView 列表，只有索引与当前 position 相等的那个被设为 true，其余全部置为 false
        for (j in textViews.indices){
            //当 isSelected 状态改变时，TextView 会自动重绘自身、
            //？
            textViews[j].isSelected=(position==j) // ③ 根据位置设置选中状态
            //(position==j时，这里是true
            //先执行右边
        }
    }
}