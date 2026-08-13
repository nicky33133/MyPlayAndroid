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
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RelativeLayout(mContext, attrs, defStyleAttr) {
    //, View.OnClickListener
    private lateinit var mTitleTv: TextView//标题文本
    private lateinit var mImgBack: ImageView//返回图标
    private lateinit var mImgRight: ImageView//右侧图标
    private lateinit var mTxtRight: TextView//右侧文字

    //titleName 和 backImageVisible 用于存储从 XML 中读取的自定义属性值
    private var titleName: String? = null
    private var backImageVisible: Boolean? = null

    init {
        //把 XML 里的字符串、尺寸、颜色等解析成一个临时对象 TypedArray（即 attr）
        val attr = context.obtainStyledAttributes(attrs, R.styleable.TitleBar)
        titleName = attr.getString(R.styleable.TitleBar_titleName)
        //backImageVisible默认值是true
        backImageVisible = attr.getBoolean(R.styleable.TitleBar_backImageVisible, true)
        attr.recycle()//立即释放这块内存资源
    }


    init {
        val binding = LayoutTitleBinding.inflate(LayoutInflater.from(context), this, true)
        binding.apply {
            mImgBack = imgBack    //标题栏左边箭头
            mTitleTv = txtTitle  //标题栏中间文字
            mImgRight = imgRight  //右边图片
            mTxtRight = txtRight  //右边文字
        }

//        mImgBack.setOnClickListener(this)
        //设置标题文本（优先使用 XML 中指定的，否则为空字符串）
        mTitleTv.text = titleName ?: ""
        //控制返回按钮的显示/隐藏
        setBackImageVisible(backImageVisible ?: true)
        //如果左边的变量 backImageVisible 不为 null，就取它的值（true 或 false）。
        //如果左边的变量 为 null，就取右边的默认值 true
    }

    fun setBackImageVisible(imageVisible: Boolean) {
        mImgBack.isVisible = imageVisible
        //.isVisible
        // 当赋值为 true 时 -> view.visibility = View.VISIBLE
        // 当赋值为 false 时 -> view.visibility = View.GONE
    }

    //设置右边图片
    fun setRightImage(imageId: Int) {
        if (imageId != 0) {
            require(mTxtRight.visibility != View.VISIBLE) { "文字和图片不可同时设置" }
            mImgRight.visibility = View.VISIBLE
            mImgRight.setImageResource(imageId)
        }
    }


    //右侧图片点击监听
    fun setRightImgOnClickListener(onClickListener: OnClickListener) {
        mImgRight.setOnClickListener(onClickListener)
    }


    //设置标题栏题目
    fun setTitle(title: String?){
        mTitleTv.text = title
    }
}