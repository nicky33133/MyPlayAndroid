package com.example.myplayandroid.base

import android.content.Context
import android.content.res.Configuration
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.model.room.entity.Article
import com.example.myplayandroid.ArticleCollectBaseFragment
import com.example.myplayandroid.article.ArticleAdapter
import com.example.myplayandroid.databinding.FragmentBaseListBinding

abstract class BaseListFragment: ArticleCollectBaseFragment() {
    //protected 就是"给继承用的锁"——只有类内部和它的子类能访问
    protected var binding: FragmentBaseListBinding? =null
    protected lateinit var articleAdapter: ArticleAdapter

    protected var page = 1

    override fun getLayoutView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToRoot: Boolean
    ): View {
        binding= FragmentBaseListBinding.inflate(inflater,container,false)
        return binding!!.root
    }
    override fun initView() {//与第一个碎片的区别是，第一个碎片在标题栏增加了右边的一个图标，这里没有
        binding?.apply {
            //baseFragmentToTop是这个FragmentBaseListBinding布局视图的id
            //根据 横竖屏状态 动态设置列表布局
            baseFragmentToTop.setRecyclerViewLayoutManager(resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT)
           //将 articleAdapter（文章列表适配器）注入到内部封装的 RecyclerView 中，完成数据与视图的绑定

//            articleAdapter= ArticleAdapter(Context,ArrayList<Article>,false)


            baseFragmentToTop.setAdapter(articleAdapter)

          //设置 刷新监听器。第一个 Lambda 是 下拉刷新 回调，
            //第二个是 上拉加载更多（加载下一页）回调
            baseFragmentToTop.onRefreshListener({
                page = 1
                refreshData()
            },{
                page++
                refreshData()
            })
        }
    }

}