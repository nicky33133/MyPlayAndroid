package com.example.myplayandroid

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.view.isVisible
import com.example.myplayandroid.databinding.LayoutTitleBinding

class TitleBar @JvmOverloads constructor(
    private val mContext: Context,
    attrs: AttributeSet?=null,
    defStyleAttr: Int=0
): RelativeLayout(mContext,attrs,defStyleAttr), View.OnClickListener {
    private lateinit var mTitleTv: TextView//标题文本
    private lateinit var mImgBack: ImageView//返回图标
    private lateinit var mImgRight: ImageView//右侧图标
    private lateinit var mTxtRight: TextView//右侧文字

    //titleName 和 backImageVisible 用于存储从 XML 中读取的自定义属性值
    private var titleName: String? =null
    private  var backImageVisible: Boolean?=null


    init {
        val attr=context.obtainStyledAttributes(attrs,R.styleable.TitleBar)
      //初始化
        titleName=attr.getString(R.styleable.TitleBar_titleName)
        backImageVisible=attr.getBoolean(R.styleable.TitleBar_backImageVisible,true)
        attr.recycle()////释放资源
    }
    init {   // 方式一：直接 inflate 布局到当前 ViewGroup
       View.inflate(mContext,R.layout.layout_title,this)
        // 方式二：使用 ViewBinding 再次 inflate（注意：这样会重复添加）
        val binding= LayoutTitleBinding.inflate(LayoutInflater.from(context),this,true)
        binding.apply {
            mImgBack=imgBack
            mTitleTv=txtTitle
            mImgRight=imgRight
            mTxtRight=txtRight
        }
        mImgBack.setOnClickListener(this)
        mTitleTv.text=titleName?:""//设置标题文本（优先使用 XML 中指定的，否则为空字符串）
        setbackImageVisible(backImageVisible?:true)
    }

    //设置返回按钮图片是否显示
    fun setbackImageVisible(imageVisible: Boolean){
        mImgBack.isVisible=imageVisible

    }
}