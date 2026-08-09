package com.example.myplayandroid.project

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.core.util.getStatusBarHeight
import com.example.core.view.custom.FragmentAdapter
import com.example.myplayandroid.databinding.FragmentProjectBinding
import com.example.myplayandroid.project.list.BaseTabFragment
import com.example.myplayandroid.project.list.ProjectListFragment
import com.example.myplayandroid.project.list.ProjectViewModel
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProjectFragment: BaseTabFragment() {

    //而不是  private val viewModel by viewModels<ProjectViewModel>()
    private val viewModel by viewModels<ProjectViewModel>()


    private var binding: FragmentProjectBinding? = null
    private lateinit var adapter: FragmentAdapter

    override fun getLayoutView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToRoot: Boolean
    ): View {
        binding= FragmentProjectBinding.inflate(inflater, container, false)
        return binding!!.root  // 返回根视图
    }

    //初始化Tab和ViewPager
    override fun initView() {
        //自定义适配器FragmentAdapter
        //projectViewPager2的适配器
        adapter= FragmentAdapter(requireActivity().supportFragmentManager,lifecycle)
        binding?.apply {
            //给控件projectViewPager2设置适配器
           projectViewPager2.adapter=adapter


            projectTabLayout.addOnTabSelectedListener(this@ProjectFragment)
        //addOnTabSelectedListener 是 TabLayout 用来监听 Tab 选中状态变化的方法。
            //“Tab”（标签页）指的就是导航栏上那个可点击的“按钮/卡片”
        //当用户点击某个 Tab、滑动 ViewPager2 导致 Tab 切换，
        //或者代码主动选中某个 Tab 时，它会触发相应的回调

            TabLayoutMediator(projectTabLayout,projectViewPager2){
                tab,position ->
                tab.text=adapter.title(position)
                //右边的title
                //   fun title(position: Int): String{
                //        return mTitles[position]
                //        //private lateinit var mTitles: Array<String>
                //    }
            }.attach()//整个联动就生效

            //TabLayoutMediator 内部大致做了以下工作：
            // 从 ViewPager2 的 Adapter 获取页面数量（getItemCount()），然后在 TabLayout 中创建相同数量的标签。
            //为每个标签执行你提供的 lambda 回调，进行内容填充。
            //注册 ViewPager2 的 registerOnPageChangeCallback，当页面滑动时更新 TabLayout 的选择状态。
            //注册 TabLayout 的 addOnTabSelectedListener，当标签点击时调用 ViewPager2.setCurrentItem() 切换页面。
            //自动处理配置变更（如屏幕旋转）后的状态恢复

            //设置 TabLayout 的内边距（padding）
            projectTabLayout.setPadding(0,context.getStatusBarHeight(),0,0)
        }
    }

    //动态生成顶部 Tab 的标题列表和对应的 Fragment 页面列表
    @SuppressLint("NotifyDataSetChanged")
    override fun initData(){
        startLoading() // 显示加载中状态（来自 BaseFragment）

        //观察 ViewModel 中的 LiveData，
        // 在数据变化时动态更新 ViewPager2 的页面和对应的 Tab 标题
        setDataStatus(viewModel.dataLiveData){
            val nameList=mutableListOf<String>()
            val viewList=mutableListOf<Fragment>()

            //构建数据列表
            //it 是网络请求返回的 Project 列表
            //forEach?
            it.forEach { project ->
                //顶部栏内容
                nameList.add(project.name)

                //zhe
                //第二个碎片的条目
                viewList.add(ProjectListFragment.newInstance(project.id))

            }
            //更新适配器
            adapter.apply {
                resetTitles(nameList.toTypedArray())
                resetFragment(viewList)
                notifyDataSetChanged()
            }
            binding?.projectViewPager2?.currentItem=viewModel.position
            //ViewPager2
            //projectViewPager2 是你 XML 中定义的一个 ViewPager2 控件。
            //currentItem 是它当前显示的页面索引（从 0 开始）。
            //viewModel.position 记录了你希望它跳转到哪一页
            //当用户旋转屏幕或切换深色模式导致 Activity重建后，ViewModel中的position值没有被销毁，
            //你的这行代码会让 ViewPager2 瞬间跳转到用户上一次浏览的那个页面，而不是回到第一页
        }
    }

    //保存当前选中的 Tab
    override fun onTabPageSelected(position: Int) {
        viewModel.position=position
    }

    companion object{
        @JvmStatic

        fun newInstance()= ProjectFragment()
    }
}