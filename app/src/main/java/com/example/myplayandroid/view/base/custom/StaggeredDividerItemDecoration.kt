package com.example.myplayandroid.view.base.custom

import android.content.Context
import android.graphics.Rect
import android.util.TypedValue
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

class StaggeredDividerItemDecoration (private val context: Context,private val interval: Int=5):
    RecyclerView.ItemDecoration(){
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val params=view.layoutParams as StaggeredGridLayoutManager.LayoutParams
        val spanIndex=params.spanIndex

        val interval= TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,interval.toFloat(),context.resources.displayMetrics
        ).toInt()


        if (spanIndex%2==0){
            outRect.left=0
        }else{
            outRect.left=interval
        }
    }
}
