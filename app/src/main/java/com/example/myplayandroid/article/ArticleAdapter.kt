package com.example.myplayandroid.article

import android.content.Context
import android.text.TextUtils
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.core.util.getHtmlText
import com.example.model.room.entity.Article
import com.example.myplayandroid.R
import kotlin.apply


//使用Brvah(万能RecyclerView适配器框架
//条目recyclerView的适配器
class ArticleAdapter(
    private val mContext: Context,
    //适配器的参数传递
    private val articleList: ArrayList<Article>,
    private val isShowCollect: Boolean = true,
    //加载条目栏的布局
) : BaseQuickAdapter<Article, BaseViewHolder>(
    //第一个泛型 String：表示列表项的数据类型，即 convert 方法中 item 的类型
    //第二个泛型 BaseViewHolder：表示 ViewHolder 的类型，这里直接使用框架提供的 BaseViewHolder
    //BaseViewHolder 是 BRVAH 提供的通用 ViewHolder，可以通过 holder.getView(id) 获取子控件
    R.layout.adapter_article
) {
    override fun convert(
        itemHolder: BaseViewHolder,
        itemData: Article //数据
    ) {
        itemHolder.apply {
            val articleTvTitle= if (!TextUtils.isEmpty(itemData.title)) {
             getHtmlText(itemData.title)
        }else{""}
            itemHolder.setText(R.id.articleTvTitle,articleTvTitle)

            itemHolder.setText(R.id.articleTvChapterName,itemData.superChapterName)

            val articleTvAuthor=if (TextUtils.isEmpty(itemData.author)){
                itemData.shareUser
            }else{
                itemData.author
            }
            itemHolder.setText(R.id.articleTvAuthor,articleTvAuthor)

           val articleIvImg=getView<ImageView>(R.id.articleIvImg)
            if (!TextUtils.isEmpty(itemData.envelopePic)){
                articleIvImg.visibility=VISIBLE
                Glide.with(mContext).load(itemData.envelopePic).into(articleIvImg)
            }else{
                articleIvImg.visibility=GONE
            }
            itemHolder.setVisible(R.id.articleTvTop,itemData.type>0)
            itemHolder.setVisible(R.id.articleTvNew,itemData.fresh)
        }
    }

}