package com.example.myplayandroid.profile.share

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.lifecycle.MutableLiveData
import com.example.model.model.CoinInfo
import com.example.model.model.ShareModel
import com.example.model.room.entity.Article
import com.example.myplayandroid.R
import com.example.myplayandroid.article.ArticleAdapter
import com.example.myplayandroid.base.ToTopRecyclerView
import com.example.myplayandroid.databinding.ActivityShareBinding
import com.example.myplayandroid.home.ArticleCollectBaseActivity
import dagger.hilt.android.AndroidEntryPoint

const val IS_MINE = "IS_MINE"
const val USER_ID = "USER_ID"


@AndroidEntryPoint
class ShareActivity : ArticleCollectBaseActivity(), View.OnClickListener {


    private lateinit var binding: ActivityShareBinding
    private val viewModel by viewModels<ShareViewModel>()
    private lateinit var articleAdapter: ArticleAdapter
    private var isMine: Boolean = true
    private var userId: Int = 0
    private var page = 1


    override fun getLayoutView(): View {
        binding = ActivityShareBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onClick(v: View?) {

    }

    override fun initView() {
        //TextView:shareTvRank
        //错误写法
        //在 apply 的作用域内，this 不再代表当前的 Activity/Fragment，而是代表 binding 对象本身
//        binding.apply{
//            shareTvRank.setOnClickListener(this)
//        }
        binding.shareTvRank.setOnClickListener(this)//this指代当前Activity
        binding.shareToTopRecyclerView.setRecyclerViewLayoutManager(true)

        articleAdapter = ArticleAdapter(this, viewModel.articleList)


        binding.shareToTopRecyclerView.setAdapter(articleAdapter)
        binding.shareToTopRecyclerView.onRefreshListener({
            page = 1;getArticleList()//分号显式分隔
        },{
            page++  //换行自动充当分隔符
            getArticleList()})

        //返回，点击事件
        val imgBack=binding.shareTitleBar.findViewById<ImageView>(R.id.imgBack)
        imgBack.setOnClickListener {
            finish()
        }

    }


    override fun initData() {
        //取出传入的键值
        isMine = intent.getBooleanExtra(IS_MINE, true)
        userId = intent.getIntExtra(USER_ID, 0)

        //设置标题
        if (!isMine) binding.shareTitleBar.setTitle(getString(R.string.author_share))

        if (isMine) {
            //获取我的分享列表
            setDataStatus(viewModel.articleLiveData) {
                setArticleData(it)
            }
        } else {
            setDataStatus(viewModel.articleAndCidLiveData) { setArticleData(it) }
        }
        getArticleList()
    }

    private fun getArticleList() {
        if (viewModel.articleList.size <= 0)
            if (isMine) {
                viewModel.getArticleListMine(page)
            } else {
                viewModel.getArticleList(userId, page)
            }

//        Log.d("TAG", "cid = ${us}")
    }


    private fun setUserInfo(coinInfo: CoinInfo) {
        //控件可见性 展示框的内容
        binding.shareHeadLl.visibility = View.VISIBLE
        binding.shareTvName.text = coinInfo.username
        binding.shareTvRank.text =
            getString(R.string.man_info, coinInfo.level, coinInfo.rank, coinInfo.coinCount)

    }


    private fun setArticleData(shareModel: ShareModel) {
        if (page == 1 && viewModel.articleList.size > 0) {
            viewModel.articleList.clear()//清空数据列表
        }

        setUserInfo(shareModel.coinInfo)
        //给数据列表添加数据
        viewModel.articleList.addAll(shareModel.shareArticles.datas)
        //如果数据列表是空，就提示
        if (viewModel.articleList.size == 0) {
            showNoContentView(getString(R.string.no_data))
        }
        //通知更新
        articleAdapter.setNewInstance(shareModel.shareArticles.datas as MutableList)
    }

    companion object {
        fun actionStart(context: Context, isMine: Boolean, userId: Int = 0) {
            val intent = Intent(context, ShareActivity::class.java).apply {
                //存入两个键值
                putExtra(IS_MINE, isMine)
                putExtra(USER_ID, userId)
            }
            context.startActivity(intent)
        }
    }
}