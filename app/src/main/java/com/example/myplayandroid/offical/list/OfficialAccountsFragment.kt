package com.example.myplayandroid.offical.list

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.core.view.custom.FragmentAdapter
import com.example.model.room.entity.ProjectClassify
import com.example.myplayandroid.BaseFragment
import com.example.myplayandroid.databinding.FragmentOfficialAccountsBinding
import com.example.myplayandroid.databinding.FragmentProjectBinding
import com.example.myplayandroid.offical.OfficialListFragment
import com.example.myplayandroid.project.list.BaseTabFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class OfficialAccountsFragment : BaseTabFragment() {

    companion object{
        @JvmStatic
            fun newInstance() = OfficialAccountsFragment()

    }
    //要获取viewModel,先自定义viewModel类
    private val viewModel by viewModels<OfficialViewModel>()//zhe
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

    // 👈 2. 在 onCreate 中解析 Bundle
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        arguments?.let {
//            it.getInt(projectId) // 假设 key 是 "KEY_PROJECT_ID"
//        }
//    }

    override fun initView() {

        //适配器初始化
        adapterAff = FragmentAdapter(requireActivity().supportFragmentManager, lifecycle)
        //给officialViewPager2设置适配器
        binding?.apply {
            officialViewPager2.adapter = adapterAff
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

//        val clickItem: ProjectClassify=_root_ide_package_.com.example.model.room.entity.ProjectClassify.id

            // 👈 3. 传入解析到的 projectId
//        val viewList = mutableListOf<Fragment>()





            adapterAff.apply {
                //重置标题数组
//                resetTitles(nameList.toTypedArray())
                //重置 Fragment 列表
                resetFragment(viewList)
                //通知 ViewPager2 刷新
                notifyDataSetChanged()
            }

        }

        fun onTabPageSelected(position: Int) {
            TODO("Not yet implemented")
        }

//        companion object {
//            @JvmStatic
//            fun newInstance() = OfficialAccountsFragment()


            //fragment传参
//        @JvmStatic
//        fun newInstance(projectClassifyId: Int): OfficialAccountsFragment {
//            val fragment = OfficialAccountsFragment()
//            val args = Bundle()
//            args.putInt("id", projectClassifyId) // 存入 Bundle
//            fragment.arguments = args
//            return fragment
//        }
//        }


    }

    override fun onTabPageSelected(position: Int) {}

}
//    override fun onTabPageSelected(position: Int) {
//
//    }




