package com.example.myplayandroid.profile.rank.list

import android.content.Context
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.model.model.Rank
import com.example.myplayandroid.R  //注意R的正确引入如果引入了其它模块，布局文件就可能导入出错
import com.example.myplayandroid.profile.share.ShareActivity
import dagger.hilt.android.internal.Contexts

class RankAdapter(
    private val mContext: Context,
    private val rankList: ArrayList<Rank>
) : BaseQuickAdapter<Rank, BaseViewHolder>(R.layout.adapter_rank) {
    override fun convert(
        itemholder: BaseViewHolder,
        itemData: Rank
    ) {
        itemholder.apply {
            setText(R.id.rankAdTvUsername, itemData.username)

            setText(R.id.rankAdTvRank, mContext.getString(R.string.ranking, itemData.rank))
            setText(R.id.rankAdTvCoinCount, mContext.getString(R.string.coin, itemData.coinCount))
            setText(R.id.rankAdTvTime, mContext.getString(R.string.lever, itemData.level))

            //打开分享界面
            getView<RelativeLayout>(R.id.rankAdRlItem).setOnClickListener {
                ShareActivity.actionStart(
                    //按下这个item时传入三个参数
                    mContext, false, itemData.userId
                )
            }
        }
    }
}