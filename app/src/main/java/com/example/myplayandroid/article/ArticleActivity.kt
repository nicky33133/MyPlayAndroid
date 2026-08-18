package com.example.myplayandroid.article

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.core.util.getHtmlText
import com.example.core.view.base.lce.BaseActivity
import com.example.model.room.entity.Article
import com.example.myplayandroid.R
import com.example.myplayandroid.databinding.ActivityArticleBinding
import dagger.hilt.android.AndroidEntryPoint


const val PAGE_NAME="PAGE_NAME"
const val PAGE_URL = "PAGE_URL"
const val PAGE_ID = "PAGE_ID"
const val ORIGIN_ID = "ORIGIN_ID"
const val USER_ID = "USER_ID"
const val IS_COLLECTION = "IS_COLLECTION"

@AndroidEntryPoint
class ArticleActivity : BaseActivity(), View.OnClickListener {

    private lateinit var binding: ActivityArticleBinding
    private var pageName = ""
    private var pageUrl = ""
//    private var pageId = -1
//    private var originId = -1
//    private var userId = -1
//    private var isCollection = -1

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
////        enableEdgeToEdge()
////        setContentView(R.layout.activity_article)
//    }

    override fun getLayoutView(): View {

       binding= ActivityArticleBinding.inflate(layoutInflater)
        return binding.root

        // ✅ 关键：为 ImageView 设置点击监听器
        binding.articleImgBack.setOnClickListener(this)
        Log.d(TAG, "onClick: zhi")
    }


    override fun initData() {
        pageName = intent.getStringExtra(PAGE_NAME) ?: ""

        pageUrl = intent.getStringExtra(PAGE_URL) ?: ""

//        pageId = intent.getIntExtra(PAGE_ID,-1)
//        isCollection=intent.getIntExtra(IS_COLLECTION,-1)
//        originId = intent.getIntExtra(ORIGIN_ID,-1)
//        userId = intent.getIntExtra(USER_ID,-1)
        //设置布局标题内容//取出存入的标题
        binding.articleTxtTitle.text= getHtmlText(pageName)
        //设置网页//取出内容链接
        binding.articleWebView.loadUrl(pageUrl)
    }


    private val TAG = "ArticleActivity"
    override fun onClick(v: View?) {
        Log.d(TAG, "onClick: zhixing")
        if (v?.id == R.id.articleImgBack){
            if (binding.articleWebView.canGoBack()){
                binding.articleWebView.goBack()
                return
            }else{
                finish()
            }
        }

        Log.d(TAG, "onClick: 执行")
    }

    companion object{ //三种ActinStart,可以提供不同的打开方式
        fun actionStart(
            context: Context,
            article: Article
        ) {
            //点击item，打开内容页，就存入点击的标题和内容链接
            val intent = Intent(context, ArticleActivity::class.java).apply {
                putExtra(PAGE_NAME, article.title)
                putExtra(PAGE_URL, article.link)//文章的链接
                //打印网址
                Log.d("pppppp",  "actionStart: ${article.link}")
                putExtra(PAGE_ID, article.id)
                //1 true 0 false
                putExtra(IS_COLLECTION, if (article.collect) 1 else 0)
                putExtra(USER_ID, article.userId)
            }
            context.startActivity(intent)
        }

        fun actionStart(
            context: Context,
            pageName: String,
            pageUrl: String){
            val intent = Intent(context, ArticleActivity::class.java).apply {
                putExtra(PAGE_NAME,pageName)
                putExtra(PAGE_URL,pageUrl)
            }
            context.startActivity(intent)
        }
    }
}