package com.example.myplayandroid.profile.user

import android.app.Activity
import android.content.Context
import android.widget.LinearLayout
import com.blankj.utilcode.util.ToastUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.myplayandroid.R
import com.example.myplayandroid.databinding.AdapterProfileBinding

//修改数据类型为自定义的数据类ProfileItem
class ProfileAdapter(val mContext: Context): BaseQuickAdapter<ProfileItem, BaseViewHolder>(R.layout.adapter_profile){
    override fun convert(
        holder: BaseViewHolder,
        item:ProfileItem
    ) {
        //条目上控件的id
        //profileAdTvTitled的类型是TextView，要用setText
        holder.setText(R.id.profileAdTvTitle,item.title)
        //profileAdIv的类型是 ImageView，要用setImageResource
        holder.setImageResource(R.id.profileAdIv,item.imgId)
//        holder.getView<LinearLayout>(R.id.profileAdLlItem).setOnClickListener {
//            when(item.title){
//                "Github"->ToastUtils.showShort("Github")
//                else->{}
//            }
//        }

        val l = holder.getView<LinearLayout>(R.id.profileAdLlItem)
            l.setOnClickListener {
            when(item.title){
                mContext.getString(R.string.about_me)-> {
                    UserActivity.actionStart(mContext)
                }
                else -> {
                    ToastUtils.showShort("about me")
                }
            }
        }


    }
}
data class ProfileItem(
    var title: String,
    var imgId: Int,

)