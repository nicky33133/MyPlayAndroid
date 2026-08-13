package com.example.myplayandroid.base

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.core.view.base.lce.BaseActivity
import com.example.myplayandroid.R
import com.example.myplayandroid.databinding.ActivityBaseListBinding
import com.example.myplayandroid.home.ArticleCollectBaseActivity
import com.example.myplayandroid.home.article.ArticleListActivity
import dagger.hilt.android.AndroidEntryPoint

private const val KEYWORD = "KEYWORD"
@AndroidEntryPoint
abstract class BaseListActivity : ArticleCollectBaseActivity(){

    protected lateinit var binding: ActivityBaseListBinding
    //protected 让子类可以直接使用 binding.tvTitle.text = "..."，极大地提升了开发效率

    //通过 protected，我们向子类保证：“这个 binding 会在 onCreate 后被赋值（非空）”。
    // 子类使用时无需担心空指针，配合 lateinit 省去了繁琐的判空逻辑


    protected  var page = 1
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//    }

    override fun getLayoutView(): View {
        binding= ActivityBaseListBinding.inflate(layoutInflater)
        return binding.root
    }

    //父布局的视图加载，影响到子类
    override fun initView() {
        binding.baseListToTop.setRecyclerViewLayoutManager(resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT)

        binding.baseListToTop.onRefreshListener({
            page = 1
            getDataList()
        }, {
            page++
            getDataList()
        })
    }
    override fun initData() {
        getDataList()//记录
    }

    abstract fun getDataList()

    companion object{
        fun actionStart(context: Context,keyword:String){
            val intent = Intent(context, ArticleListActivity::class.java).apply {
                putExtra(KEYWORD, keyword)
            }
            context.startActivity(intent)
        }
    }
}