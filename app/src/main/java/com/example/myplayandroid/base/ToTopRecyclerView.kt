package com.example.myplayandroid.base

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.myplayandroid.StaggeredDividerItemDecoration
import com.example.myplayandroid.article.ArticleAdapter
import com.example.myplayandroid.databinding.LayoutToTopBinding
//import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smart.refresh.layout.SmartRefreshLayout//正确导入
import kotlin.system.measureTimeMillis

class ToTopRecyclerView @JvmOverloads constructor(
    private val mContext: Context,
    attrs: AttributeSet?=null,
    defStyleAttr: Int=0
): FrameLayout(mContext,attrs,defStyleAttr), View.OnClickListener {

    private lateinit var mToTopSmartRefreshLayout: SmartRefreshLayout
    private lateinit var mToTopRecyclerView: RecyclerView//条目recyclerView
    private lateinit var mToTopIvClick: ImageView//回到顶部图标
    private  var mLoadTime=1000//最小动画时长（1000ms）

    init {
        initView()
    }
    private fun initView(){
        val binding= LayoutToTopBinding.inflate(LayoutInflater.from(context),this,true)
        binding.apply {
            mToTopSmartRefreshLayout=toTopSmartRefreshLayout
            mToTopRecyclerView=toTopRecycleView
            mToTopIvClick=toTopIvClick
            //点击监听、
            mToTopIvClick.setOnClickListener(this@ToTopRecyclerView)

            //当条目滚动时，回到顶部按钮的隐藏
            mToTopRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener(){
                override fun onScrolled(
                    recyclerView: RecyclerView,
                    dx: Int,
                    dy: Int
                ) {
                    //-1 表示“能否向上滚动”，如果返回 false，说明已经在顶部，此时隐藏按钮。
                    if (!recyclerView.canScrollVertically(-1)){
                        mToTopIvClick.visibility= View.GONE
                    }else if (dy<0){//手指向下滑动（列表向上滚动），显示按钮
                        mToTopIvClick.visibility=View.VISIBLE
                    }else if (dy>0){//手指向上滑动（列表向下滚动），隐藏按钮
                        mToTopIvClick.visibility= View.GONE
                    }
                }
            })
        }

    }

    override fun onClick(v: View?) {
        //平滑滚动到列表头部
        mToTopRecyclerView.smoothScrollToPosition(0)
    }

    //布局管理器
    //设置首页滚动条目的布局
    fun setRecyclerViewLayoutManager(isLinearLayout: Boolean){
        if (isLinearLayout){
            mToTopRecyclerView.layoutManager= LinearLayoutManager(context)
        }else {
        val spanCount=2//两列瀑布流
            val layoutManager= StaggeredGridLayoutManager(spanCount, StaggeredGridLayoutManager.VERTICAL)
            mToTopRecyclerView.layoutManager=layoutManager
            //禁用默认的空白处理策略
            layoutManager.gapStrategy= StaggeredGridLayoutManager.GAP_HANDLING_NONE

            //添加分割线
            //通过自定义的 StaggeredDividerItemDecoration 添加 item 间距
            mToTopRecyclerView.addItemDecoration(
                StaggeredDividerItemDecoration(context)
            )
            //滚动监听
            //修复瀑布流布局中因 spanIndex 分配异常导致的显示错乱问题
            mToTopRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(
                    recyclerView: RecyclerView,
                    newState: Int
                ) {
                    val first= IntArray(spanCount)//spanCount 是瀑布流的总列数
                    //重新分配跨度findFirstCompletelyVisibleItemPositions
                    layoutManager.findFirstCompletelyVisibleItemPositions(first)
                    //first[0] 对应第 0 列，first[1] 对应第 1 列
                    //只有当 newState == SCROLL_STATE_IDLE 时
                    // （即手指离开屏幕且滚动完全停止），才执行后续操作，避免频繁触发
                    if (newState== RecyclerView.SCROLL_STATE_IDLE&&(first[0]==1||first[1]==1)){
                        layoutManager.invalidateSpanAssignments()//废弃当前所有 item 的 span 分配
                    }
                }
            })
        }
    }



    //设置刷新/加载监听（onRefreshListener）
    //据数据加载的实际耗时，动态调整下拉刷新或上拉加载动画的结束时间
    //设置的 SmartRefreshLayout 包裹着 RecyclerView。这个方法控制的是“数据请求的触发动作”，
    // 而这个动作的直接结果就是更新 RecyclerView 的数据源
    fun  onRefreshListener(onRefreshListener: ()-> Unit,onLoadMoreListener: ()-> Unit){
        mToTopSmartRefreshLayout.apply {
             //测量onRefreshListener.invoke()的执行耗时
            // 结束刷新动画
            setOnRefreshListener { relayout->
                relayout.finishRefresh(measureTimeMillis { onRefreshListener.invoke() }.toInt())
            }
            //加载更多数据
            //结束加载状态
            //mLoadTime 是一个预设的最小时间（比如 1000ms）
            setOnLoadMoreListener { relayout->
                val time=measureTimeMillis { onLoadMoreListener.invoke() }.toInt()
                relayout.finishLoadMore(if (time>mLoadTime)time else mLoadTime)
            }
        }
    }


    //设置Adapter
    //setHasStableIds是RecyelerView的方法
    fun setAdapter(adapter: RecyclerView.Adapter<*>){
        adapter.setHasStableIds(true)
        mToTopRecyclerView.adapter=adapter
    }


}