package com.example.myplayandroid.profile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myplayandroid.ArticleCollectBaseFragment
import com.example.myplayandroid.Play
import com.example.myplayandroid.R
import com.example.myplayandroid.databinding.FragmentProfileBinding
import com.example.myplayandroid.profile.rank.list.RankActivity
import com.example.myplayandroid.profile.user.ProfileAdapter
import com.example.myplayandroid.profile.user.ProfileItem

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
//        //跟视图是FrameLayout
//        val view = inflater.inflate(R.layout.fragment_profile, container, false)
//        return view
    }

    override fun initView() {
        //设置右侧标题栏图片
        binding?.profileTitleBar?.setRightImage(R.drawable.btn_right_right_bg)
        binding?.profileRv?.layoutManager = LinearLayoutManager(requireContext())
        //右侧图片点击事件
        //自定义控件TitleBar中的方法
        binding?.profileTitleBar?.setRightImgOnClickListener {
            RankActivity.actionStart(requireContext())
        }


        // ProfileAdapter主构造方法只有一个参数：mContext: Context。
        //在 Activity 中，直接传入 this；在 Fragment 中，传入 requireContext()
        profileAdapter = ProfileAdapter(requireContext())
        binding?.profileRv?.adapter = profileAdapter


        //头像、昵称、用户名、退出登录按钮都设置了 this@ProfileFragment 作为监听器，
        // 点击事件统一在 onClick 中处理
       binding?.apply {
           profileIvHead.setOnClickListener(this@ProfileFragment)
           profileTvName.setOnClickListener(this@ProfileFragment)
           profileTvRank.setOnClickListener(this@ProfileFragment)
          profileBtnLogout.setOnClickListener(this@ProfileFragment)
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
            // 清空旧数据，避免重复添加
            profileItemList.clear()
            for (index in nameArray.indices) {
                profileItemList.add(
                    ProfileItem(
                        nameArray[index],
                        imageArray[index]
                    )

                )
            }
            //通知适配器插入数据
            //notifyDataSetChanged() 没有参数，调用它时直接写
            //profileAdapter.notifyDataSetChanged()
            //这个
//            profileAdapter.notifyItemInserted(profileItemList.size)

            // 将数据设置给适配器（会自动刷新），这个是系统的，会自己使用通知方法
            profileAdapter.setList(profileItemList)
        }
    }

    //刷新登录状态
    override fun refreshData() {
        if (Play.isLogin()){
            binding.apply {
                this?.profileIvHead?.setImageResource(R.drawable.head)
                this?.profileTvName?.text=Play.nickname
                this?.profileTvRank?.text= Play.username
                this?.profileBtnLogout?.visibility= View.VISIBLE
            }
        }else{
            clearInfo()
        }
    }

    override fun onClick(v: View?) {

    }

    //显示未登录状态
    private fun clearInfo(){
        binding?.apply{
            profileBtnLogout.visibility= View.GONE
            profileIvHead.setImageResource(R.drawable.img_normal_head)
            profileTvName.text=getString(R.string.no_login)
            profileTvRank.text=getString(R.string.click_login)
        }
    }

}