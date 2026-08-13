package com.example.myplayandroid.home.article

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.activity.viewModels
import com.example.model.room.entity.Article
import com.example.myplayandroid.R
import com.example.myplayandroid.article.ArticleAdapter
import com.example.myplayandroid.base.BaseListActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlin.getValue


private const val KEYWORD = "KEYWORD"

@AndroidEntryPoint
class ArticleListActivity : BaseListActivity() {

    private val viewModel by viewModels<ArticleListViewModel>()

    private lateinit var articleAdapter: ArticleAdapter


//    var articleList = ArrayList<Article>()


    private var keyword = ""

    override fun initView() {
        super.initView()

        articleAdapter = ArticleAdapter(
            this,
            viewModel.dataList
        )
        binding.baseListToTop.setAdapter(articleAdapter)

        //设置点击事件
        articleAdapter.setOnItemClickListener { adapter, view, position ->
            val article=adapter.data[position]
            Toast.makeText(this, "点击了：${article.toString()}", Toast.LENGTH_SHORT).show()
        }


    }

    //    UI初始化 + 结果观察者注册	仅执行 1次（页面创建时）
    override fun initData() {
        keyword = intent.getStringExtra(KEYWORD) ?: ""
        binding.baseListTitleBar.setTitle(keyword)

        super.initData()//注意点，关于顺序问题

        setDataStatus(viewModel.dataLiveData) {
            if (page == 1 && viewModel.dataList.size > 0) {
                viewModel.dataList.clear()
            }
            viewModel.dataList.addAll(it.datas)

            if (viewModel.dataList.size == 0) {
                showNoContentView(getString(R.string.keyword_null, keyword))
            }
            articleAdapter.setNewInstance(viewModel.dataList)
        }

//        super.initData()
    }


    //网络请求触发器	执行 N次（首次加载、下拉刷新、加载更多）
    override fun getDataList() {
        if (viewModel.dataList.size <= 0) startLoading()
        viewModel.getDataList(QueryKeyArticle(page, keyword))
    }


    companion object {
        fun actionStart(context: Context, keyword: String) {
            val intent = Intent(context, ArticleListActivity::class.java).apply {
                //存入一个值
                putExtra(KEYWORD, keyword)
            }
            context.startActivity(intent)
        }
    }
}