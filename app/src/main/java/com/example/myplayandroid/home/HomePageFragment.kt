package com.example.myplayandroid.home

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.myplayandroid.ArticleCollectBaseFragment
import com.example.myplayandroid.databinding.FragmentHomePageBinding
import com.example.myplayandroid.R
import com.example.myplayandroid.article.ArticleAdapter
import com.example.myplayandroid.home.search.SearchActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint

class HomePageFragment : ArticleCollectBaseFragment() {
    private lateinit var articleAdapter: ArticleAdapter
    private var page = 1

    //viewModel储存数据
    private val viewModel by viewModels<HomePageViewModel>()
    private var binding: FragmentHomePageBinding? = null
    override fun getLayoutView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToRoot: Boolean
    ): View {
        binding = FragmentHomePageBinding.inflate(inflater, container, attachToRoot)
        return binding!!.root
    }

    override fun initView() {
        binding?.apply {
            //设置题栏右侧图标
            homeTitleBar.setRightImage(R.drawable.home_search_button)

            //点击事件，点击图标就跳转到搜索页
            homeTitleBar.setRightImgOnClickListener {
                SearchActivity.actionStart(requireContext())
                activity?.overridePendingTransition(R.anim.search_push_in, R.anim.fake_anim)
            }


            //文章列表（RecyclerView）+ 刷新控制
            homeToTopRecyclerView.setRecyclerViewLayoutManager(true)

            //viewModel.articleList：数据源引用（ArrayList<Article>），适配器直接持有它
            articleAdapter = ArticleAdapter(requireContext(), viewModel.articleList)
            //设置刷新和加载更多的监听器
            homeToTopRecyclerView.onRefreshListener({
                page = 1//下拉刷新	,page = 1（重置页码）
                getArticleList(true)
            }, {
                page++//上拉加载更多
                getArticleList(true)
            })
            homeToTopRecyclerView.setAdapter(articleAdapter)
        }
    }

    //加载首个碎片的recyclerView的数据
    override fun initData() {
        startLoading()// 显示加载状态

        // fun <T>setDataStatus(
        //        dataLiveData: LiveData<Result<T>>,// 1. 数据源
        //        onBadNetwork:()-> Unit={},// 2. 失败时的额外操作（可选）
        //        onDataStatus:(T)-> Unit // 3. 成功时处理数据的操作（必填）
        //    ){}
        //第一个参数要求是 LiveData<Result<T>>

        //绑定文章列表数据
        setDataStatus(viewModel.articleLiveData, {
            if (viewModel.articleList.size > 0) loadFinished()
        }) {
            Log.e(TAG, "initData: ")
            if (page == 1 && viewModel.articleList.size > 0) {
                viewModel.articleList.clear()
            }
            viewModel.articleList.addAll(it)

            //Brvah适配器的通知方式
            //articleAdapter.notifyItemInserted(it.size)
//            adapterA.setNewInstance(data)

            articleAdapter.setNewInstance(it)
        }
        getArticleList(false) // 首次加载第一页

    }

    override fun refreshData() {
        getArticleList(true)
    }

    //将 Fragment/Activity 中的数据请求操作，转发给 ViewModel 去执行
    private fun getArticleList(isRefresh: Boolean) {
        viewModel.getArticleList(page, isRefresh)
    }

    companion object {
        private const val TAG = "HomePageFragment"

        @JvmStatic
        //加上 @JvmStatic 后，Java 代码就可以像调用静态方法一样直接写：
        // HomePageFragment.newInstance()，和原生 Java 写法的体验一致
        fun newInstance() = HomePageFragment()
        //fun newInstance() = HomePageFragment() 等价于：
        // fun newInstance(): HomePageFragment {
//            return HomePageFragment()
//        }
//        它的作用是创建一个 HomePageFragment 的新实例并返回
    }
}