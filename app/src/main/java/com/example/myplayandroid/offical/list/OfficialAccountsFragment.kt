package com.example.myplayandroid.offical.list

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.core.util.getStatusBarHeight
import com.example.core.view.custom.FragmentAdapter
import com.example.model.room.entity.ProjectClassify
import com.example.myplayandroid.BaseFragment
import com.example.myplayandroid.databinding.FragmentOfficialAccountsBinding
import com.example.myplayandroid.databinding.FragmentProjectBinding
import com.example.myplayandroid.offical.OfficialListFragment
import com.example.myplayandroid.project.list.BaseTabFragment
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class OfficialAccountsFragment : BaseTabFragment() {

    //要获取viewModel,先自定义viewModel类
    private val viewModel by viewModels<OfficialViewModel>()//顶部标题栏的
    private var binding: FragmentOfficialAccountsBinding? = null
    private lateinit var adapterAff: FragmentAdapter


    override fun getLayoutView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToRoot: Boolean
    ): View {
        binding = FragmentOfficialAccountsBinding.inflate(inflater, container, false)
        return binding!!.root  // 返回根视图
    }

    override fun initView() {
        //适配器初始化
        adapterAff = FragmentAdapter(requireActivity().supportFragmentManager, lifecycle)

        binding?.apply {
            //给officialViewPager2设置适配器
            officialViewPager2.adapter = adapterAff
            //给  officialTabLayout（顶部栏）设置监听器
            officialTabLayout.addOnTabSelectedListener(this@OfficialAccountsFragment)

            //TabLayoutMediator：这是官方提供的帮助类，
            // 将 TabLayout 和 ViewPager2 绑定在一起，并设置每个 Tab 的标题
            TabLayoutMediator(officialTabLayout,officialViewPager2){tab,position ->
                tab.text = adapterAff.title(position)
            }.attach()
            //设置 TabLayout 的顶部内边距
            officialTabLayout.setPadding(0,context.getStatusBarHeight(),0,0)
        }


    }

    //@SuppressLint("NotifyDataSetChanged") 是 Android 开发中用于压制（忽略）特定 Lint 警告的注解
    @SuppressLint("NotifyDataSetChanged")
    override fun initData() {
        startLoading()

        setDataStatus(viewModel.dataLiveData) {
            val nameList=mutableListOf<String>()
            val viewList = mutableListOf<Fragment>()

            //遍历 it（公众号列表），为每个公众号：
            //it很关键!!!!   it遍历顶部栏的列表
            it.forEach { project ->
                nameList.add(project.name)
                viewList.add(OfficialListFragment.newInstance(project.id))
            }

            adapterAff.apply {
                //重置标题数组
                resetTitles(nameList.toTypedArray())
                //重置 Fragment 列表
                resetFragment(viewList)
                //通知 ViewPager2 刷新
                notifyDataSetChanged()
            }

            //恢复上次选中的 Tab：
            binding?.officialViewPager2?.currentItem=viewModel.position
            //currentItem 系统的
            //position在OfficialViewModel自定义的

        }

    }

    //保存当前选中的 Tab
    override fun onTabPageSelected(position: Int) {
        viewModel.position
    }
    //提供静态方法，方便外部创建 OfficialAccountsFragment 实例
    companion object{
        @JvmStatic
        fun newInstance()= OfficialAccountsFragment()
    }

}





