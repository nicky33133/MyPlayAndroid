package com.example.myplayandroid.article

import android.content.Context
import android.text.TextUtils
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.core.util.checkNetworkAvailable
import com.example.core.util.getHtmlText
import com.example.model.room.PlayDatabase
import com.example.model.room.entity.Article
import com.example.model.room.entity.HISTORY
import com.example.myplayandroid.R
import com.example.myplayandroid.profile.share.ShareActivity
import com.example.myplayandroid.showToast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
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
), CoroutineScope by MainScope() {//继承协程作用域

//    init {
//        addChildClickViewIds(R.id.articleLlItem)
//    }

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


            //点击事件！记录！！
            itemHolder.getView<RelativeLayout>(R.id.articleLlItem).setOnClickListener {
                //检查网络
                if (!mContext.checkNetworkAvailable()){
                    mContext.showToast(mContext.getString(R.string.no_network))
                    return@setOnClickListener //返回点击事件
                }
                //zhe
                ArticleActivity.actionStart(mContext,itemData)

//                val browseHistoryDao= PlayDatabase.getDatabase(mContext).browseHistoryDao()
//                Log.d("aaaaa", "convert: Article1")
                //协程
                //Adapter	❌ 禁止	无法管理生命周期，极易内存泄漏
                //在 BRVAH 的点击事件中启动协程，一定要在 Activity/Fragment 或 ViewModel 中进行，
                // 并使用 lifecycleScope 或 viewModelScope 来确保协程的生命周期被正确管理

                //适配器继承了协程作用域，这里才可以开启协程
//                Log.d("aaaaa", "convert: Article2")
//                launch{
//                    //const val HISTORY = 10 在data class Article(中自定义的类型
//                    if (browseHistoryDao.getArticle(itemData.id, HISTORY) == null){
//                        Log.d("aaaaa", "convert: Article3")
//                        itemData.localType = HISTORY
//                        itemData.desc=""
//                        browseHistoryDao.insert(itemData)//插入数据
//                        Log.d("aaaaa", "convert: Article")
//                    }
//                }
            }

//            itemHolder.getView<LinearLayout>(R.id.shareActivity).setOnClickListener {
//                //检查网络
//                if (!mContext.checkNetworkAvailable()){
//                    mContext.showToast(mContext.getString(R.string.no_network))
//                    return@setOnClickListener //返回点击事件
//                }
//                ShareActivity.actionStart(context,false,)
//            }



        }


    }

}