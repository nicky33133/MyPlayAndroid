package com.example.myplayandroid.offical

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.example.model.pojo.QueryArticle
import com.example.model.room.entity.Article
import com.example.model.room.entity.PROJECT
import com.example.myplayandroid.article.ArticleAdapter
import com.example.myplayandroid.base.BaseListFragment
import com.example.myplayandroid.offical.list.OfficialListViewModel
import dagger.hilt.android.AndroidEntryPoint


const val PROJECT_CID = "PROJECT_CID"
//公众号文章列表碎片

@AndroidEntryPoint
class OfficialListFragment : BaseListFragment(){
    //有viewPager2
    private var projectCid: Int? = null
    private val viewModel by viewModels<OfficialListViewModel>()
    //无法创建 OfficiallistViewModel 的实例

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //?
        arguments?.let {
            projectCid = it.getInt(PROJECT_CID)
        }
    }

    override fun refreshData() {

        getArticleList(true)//刷新
    }

    override fun initView() {
        articleAdapter= ArticleAdapter(requireContext(),viewModel.dataList)
        super.initView()
    }


    //fun isHaveHeadMargin()：这是一个询问函数，直译是“是否有头部边距？”
    override fun isHaveHeadMargin(): Boolean {
        return false
    }
    private  fun getArticleList(isRefresh: Boolean){
        //dataList?
        if (viewModel.dataList.size <= 0) startLoading()
        //？
        projectCid?.apply {
            //QueryArticle是一个数据类
            //getDataList是给 pageLiveData.设置值的
            viewModel.getDataList(QueryArticle(this,page,isRefresh))
            //this 指向 projectCid

        }
    }

    //加载数据文章列表的
    override fun initData() {
      setDataStatus(viewModel.dataLiveData,{
          if (viewModel.dataList.size > 0) loadFinished()
      }){
          if (page == 1 && viewModel.dataList.size > 0){
              viewModel.dataList.clear()
          }
          viewModel.dataList.addAll(it)

          articleAdapter.setNewInstance(it as MutableList<Article>?)//zhe
      }
        getArticleList(false)//？不刷新
    }


    companion object{

        fun newInstance(cid: Int)=OfficialListFragment().apply {
            arguments = Bundle().apply {
                putInt(PROJECT_CID,cid)
            }
        }
    }
}