package com.example.myplayandroid.project.list

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.model.pojo.QueryArticle
import com.example.model.room.entity.Article
import com.example.myplayandroid.article.ArticleActivity.Companion.actionStart
import com.example.myplayandroid.article.ArticleAdapter
import com.example.myplayandroid.base.BaseListFragment
import com.example.myplayandroid.home.article.ArticleListActivity
//import com.example.myplayandroid.home.HomePageFragment.Companion.TAG
import dagger.hilt.android.AndroidEntryPoint
import kotlin.collections.addAll

private const val PROJECT_CID = "PROJECT_CID"//?
//第二个碎片文章列表的自定义类
@AndroidEntryPoint
class ProjectListFragment: BaseListFragment() {
    private val viewModel by viewModels<ProjectListViewModel>()
    private var projectCid:Int? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            //给 projectCid 赋值
            projectCid = it.getInt(PROJECT_CID)
        }

    }

    override fun refreshData() {
        getArticleList(true)
    }

    override fun isHaveHeadMargin(): Boolean {
        return super.isHaveHeadMargin()
    }

    override fun initView() {
        //第二个碎片条目的
        //适配器的实例
        //父类实例的初始化在子类完成
        articleAdapter= ArticleAdapter(requireContext(),viewModel.dataList)
        super.initView()

    }

    private fun getArticleList(isRefresh: Boolean){
        if (viewModel.dataList.size <= 0){
            startLoading()
            projectCid?.apply {
                viewModel.getDataList(QueryArticle(page,this,isRefresh))
                //QueryArticle是一个数据类
                //getDataList是设置  pageLiveData 的值的
                //getDataList中传入的参数是一个数据类QueryArticle
            }
        }
    }


    override fun initData() { //第二个Fragment  RecyclerView条目加载数据的
        //  setDataStatus传入两个参数，参数1是从viewModel中的dataLiveData,参数2是一个函数型参数
        setDataStatus(viewModel.dataLiveData,{ if (viewModel.dataList.size>0) loadFinished() }){
            if (page == 1 && viewModel.dataList.size>0){
                viewModel.dataList.clear()
            }
            viewModel.dataList.addAll(it)
            //通知//Bravh适配器
            articleAdapter.setNewInstance(it as MutableList<Article>?)
            //RecyclerView原生适配器的通知方式
//            articleAdapter.notifyItemInserted(it.size)
        }

        getArticleList(false)

//
//        //绑定文章列表数据
//        setDataStatus(viewModel.articleLiveData,{
//            if (viewModel.articleList.size>0) loadFinished()
//        }){
//            Log.e(TAG, "initData: ", )
//            if (page==1 && viewModel.articleList.size>0){
//                viewModel.articleList.clear()
//            }
//            viewModel.articleList.addAll(it)
//
//            //Brvah适配器的通知方式
//            //articleAdapter.notifyItemInserted(it.size)
////            adapterA.setNewInstance(data)
//
//            articleAdapter.setNewInstance(it)
//        }
//        getArticleList(false) // 首次加载第一页
    }




    companion object{
        @JvmStatic
        fun newInstance(cid: Int)= ProjectListFragment().apply {

            arguments = Bundle().apply { putInt(PROJECT_CID,cid) }
            //创建一个空的 Bundle 对象，在内部执行 putInt(PROJECT_CID, cid)，
           // 往这个 Bundle 里存入 Key 为 PROJECT_CID、Value 为 cid 的数据。
            //然后 返回这个 Bundle 对象本身
        }
        //创建 Fragment（ ProjectListFragment）实例，
       //并通过 arguments（Bundle）安全地传递参数（cid）

     //  =后面的代码块返回的是一个 ProjectListFragment 对象，
    // 所以 Kotlin 自动推断返回类型为 ProjectListFragment
   //fun newInstance(cid: Int) = ProjectListFragment().apply { ... }


        //不是简写的方式
        //// 静态工厂方法，外部调用此方法获取实例
        //        fun newInstance(cid: Int, name: String): ProjectListFragment {
        //            val fragment = ProjectListFragment()
        //            val args = Bundle().apply {
        //                putInt(PROJECT_CID, cid)
        //                putString(PROJECT_NAME, name)
        //            }
        //            fragment.arguments = args // 参数随 Fragment 生命周期永久保存
        //            return fragment
        //        }
    }
}