package com.example.myplayandroid.profile.user

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.core.view.base.lce.BaseRecyclerAdapter
import com.example.myplayandroid.databinding.AdapterProfileBinding


class ProfileAdapter (
    private val mContext: Context,
    private val profileItemList: ArrayList<ProfileItem>,
): BaseRecyclerAdapter<AdapterProfileBinding>(){
    //AdapterProfileBinding是适配器的布局

    //创建条目视图
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseRecyclerHolder<AdapterProfileBinding> {
        val binding= AdapterProfileBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return BaseRecyclerHolder(binding)
    }

    override fun getItemCount(): Int {
        return profileItemList.size
    }

    //绑定数据到视图
    override fun onBaseBindViewHolder(
        position: Int,
        binding: AdapterProfileBinding
    ) {
        val data=profileItemList[position]
        binding.apply {
            profileAdTvTitle.text = data.title
            profileAdIv.setImageResource(data.imgId)

        }
    }
}

data class ProfileItem(var title: String,var imgId: Int)