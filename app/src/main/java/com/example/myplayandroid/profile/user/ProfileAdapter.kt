package com.example.myplayandroid.profile.user

import android.app.Activity
import android.content.Context
import android.widget.LinearLayout
import com.blankj.utilcode.util.ToastUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.model.room.entity.Article
import com.example.myplayandroid.R
import com.example.myplayandroid.article.ArticleActivity
import com.example.myplayandroid.databinding.AdapterProfileBinding
import com.example.myplayandroid.main.login.LoginActivity
import com.example.myplayandroid.profile.history.BrowseHistoryActivity

//修改数据类型为自定义的数据类ProfileItem
class ProfileAdapter(val mContext: Context) :
    BaseQuickAdapter<ProfileItem, BaseViewHolder>(R.layout.adapter_profile) {
    override fun convert(
        holder: BaseViewHolder,
        item: ProfileItem
    ) {
        //条目上控件的id
        //profileAdTvTitled的类型是TextView，要用setText
        holder.setText(R.id.profileAdTvTitle, item.title)
        //profileAdIv的类型是 ImageView，要用setImageResource
        holder.setImageResource(R.id.profileAdIv, item.imgId)
//        holder.getView<LinearLayout>(R.id.profileAdLlItem).setOnClickListener {
//            when(item.title){
//                "Github"->ToastUtils.showShort("Github")
//                else->{}
//            }
//        }

        val l = holder.getView<LinearLayout>(R.id.profileAdLlItem)
        l.setOnClickListener {
            when (item.title) {
                //关于我
                mContext.getString(R.string.about_me) -> {
                    UserActivity.actionStart(mContext)
                }
                //我的收藏
                mContext.getString(R.string.my_collection) -> {
                    LoginActivity.actionStart(mContext)
                }
                //我的分数
                mContext.getString(R.string.mine_points)->{
                    LoginActivity.actionStart(mContext)
                }
                //我的博客
                mContext.getString(R.string.mine_blog)->{
                    ArticleActivity.actionStart(mContext,
                        mContext.getString(R.string.mine_blog),
                        "https://blog.csdn.net/2301_78829282?spm=1000.2115.3001.5343")
                }
                //我的浏览历史
                mContext.getString(R.string.browsing_history)->{
                    BrowseHistoryActivity.actionStart(context)
                }
                //掘金
                mContext.getString(  R.string.mine_nuggets)->{
                    ArticleActivity.actionStart(mContext,
                        mContext.getString(R.string.mine_nuggets),
                        "https://juejin.cn/user/4421551503714587")
                }
                //github
                mContext.getString(R.string.github)->{
                 ArticleActivity.actionStart(mContext,
                     mContext.getString(R.string.mine_github),
                     "https://github.com/nicky33133")
                }
            }
        }
    }
}

data class ProfileItem(
    var title: String,
    var imgId: Int,

    )