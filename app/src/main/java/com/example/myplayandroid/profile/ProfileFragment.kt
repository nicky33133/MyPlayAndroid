package com.example.myplayandroid.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myplayandroid.ArticleCollectBaseFragment
import com.example.myplayandroid.R
import com.example.myplayandroid.databinding.FragmentOfficialAccountsBinding
import com.example.myplayandroid.databinding.FragmentProfileBinding
import com.example.myplayandroid.profile.user.ProfileAdapter
import com.example.myplayandroid.profile.user.ProfileItem
import com.example.myplayandroid.project.ProjectFragment

class ProfileFragment : ArticleCollectBaseFragment(), View.OnClickListener {
        private lateinit var profileAdapter: ProfileAdapter
    private var profileItemList = ArrayList<ProfileItem>()
    private lateinit var nameArray: Array<String>
    private var binding: FragmentProfileBinding? = null

    //第四个碎片的图标
    private val imageArray = arrayOf(
        R.drawable.ic_integral,
        R.drawable.ic_profile_collect,
        R.drawable.ic_csdn,
        R.drawable.ic_history,
        R.drawable.ic_cnblogs,
        R.drawable.ic_github,
        R.drawable.ic_profile_mine
    )

    companion object {
        @JvmStatic
        fun newInstance() = ProfileFragment()
    }

//
//    override fun onCreateView(
//
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        binding = FragmentProfileBinding.inflate(inflater, container, false)
//        return binding!!.root  // 返回根视图
//    }

    override fun getLayoutView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToRoot: Boolean
    ): View {
//        //加载第四个碎片的布局文件
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding!!.root  // 返回根视图
        //跟视图是FrameLayout
    }

    override fun initView() {
        binding?.apply {
            //以“线性列表”的方式排列子项
            profileRv.layoutManager= LinearLayoutManager(context)
            //创建一个适配器（Adapter）实例，负责将数据（profileItemList）转化为具体的 Item 视图
            profileAdapter= ProfileAdapter(requireContext(),profileItemList)
            //将刚刚创建好的适配器实例挂载到 RecyclerView 上
            profileRv.adapter=profileAdapter
        }
    }

    //填充功能列表，第四个碎片的
    override fun initData() {
        if (profileItemList.isEmpty()) {
            nameArray = arrayOf(
                getString(R.string.mine_points),
                getString(R.string.my_collection),
                getString(R.string.mine_blog),
                getString(R.string.browsing_history),
                getString(R.string.mine_nuggets),
                getString(R.string.github),
                getString(R.string.about_me)
            )
            for (index in nameArray.indices) {
                profileItemList.add(
                    ProfileItem(nameArray[index], imageArray[index])
                )
            }
            //通知适配器插入数据
            //notifyDataSetChanged() 没有参数，调用它时直接写
//            profileAdapter.notifyDataSetChanged()
            profileAdapter.notifyItemInserted(profileItemList.size)
        }
    }
    override fun refreshData() {}
    override fun onClick(v: View?) {}
}