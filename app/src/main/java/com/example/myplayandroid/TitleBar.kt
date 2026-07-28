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
): RelativeLayout(mContext,attrs,defStyleAttr) {
    //, View.OnClickListener
    init {
        LayoutTitleBinding.inflate(LayoutInflater.from(context),this,true)
    }
}