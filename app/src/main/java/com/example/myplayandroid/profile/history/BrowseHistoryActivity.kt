package com.example.myplayandroid.profile.history

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.model.room.entity.Article
import com.example.myplayandroid.R
import com.example.myplayandroid.article.ArticleAdapter
import com.example.myplayandroid.base.BaseListActivity
import com.example.myplayandroid.profile.user.ProfileAdapter
import com.example.myplayandroid.showToast
import kotlin.compareTo

class BrowseHistoryActivity : BaseListActivity(), View.OnClickListener{
//布局用的是父布局的
    private val viewModel by viewModels<BrowseHistoryViewModel>()
    private lateinit var articleAdapter: ArticleAdapter


    override fun initView() {
        super.initView()
        articleAdapter= ArticleAdapter(
            this,
            viewModel.dataList,
            false
        )

        binding.baseListToTop.setAdapter(articleAdapter)
        binding.baseListTitleBar.setTitle(getString(R.string.browsing_history))
        // 通过 TitleBar 找到内部的 imgBack
        val imgBack=binding.baseListTitleBar.findViewById<ImageView>(R.id.imgBack)
        imgBack.setOnClickListener {
            finish()
        }
    }

    private  val TAG = "BrowseHistoryActivity"
    override fun initData() {
        super.initData()

        //给整体标题栏设置点击监听
//        binding.baseListTitleBar.setOnClickListener {
//            finish()
//        }




        //观察数据
        viewModel.dataLiveData.observe(this){
            if (it.isSuccess){
                //定义一个装数据的列
                val articleList= it.getOrNull()
                if (articleList != null){
                    loadFinished()//加载成功

                    //接口需要page=1//为什么在这单独判断这个？
                    if (page == 1 && viewModel.dataList.size>0){
                        //先清空数据列表
                        viewModel.dataList.clear()
                    }

                    viewModel.dataList.addAll(articleList)
                    //通知
                    // articleList 是 List<Article>（不可变）
                    articleAdapter.setNewInstance(articleList.toMutableList())

                    Log.d(TAG, "initData: success")
                }else{
                    showLoadErrorView()//显示网络错误
                    Log.d(TAG, "initData: success1")
                }
            }else{
                if (viewModel.dataList.size <= 0) {
                    //显示网络错误
                    showNoContentView(getString(R.string.no_browsing_history))
                } else {
                    //提示没有数据
                    showToast(getString(R.string.no_more_data))
                    loadFinished()
                }
                Log.d(TAG, "initData: success2")
            }
        }
    }

    override fun getDataList() {

        if (viewModel.dataList.size <=0 ){
            startLoading()
        }
        viewModel.getDataList(page)

    }

    override fun onClick(v: View?) {
        if(v?.id == R.id.articleImgBack ){
            finish() // ✅ 直接关闭当前 Activity，回到上一个页面
        }
    }

    companion object{
        fun actionStart(context: Context){
            val intent = Intent(context, BrowseHistoryActivity::class.java)
            context.startActivity(intent)
        }
    }
}