package com.example.myplayandroid.main

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.myplayandroid.R
import com.example.myplayandroid.home.HomePageFragment
import com.example.myplayandroid.offical.list.OfficialAccountsFragment
import com.example.myplayandroid.profile.ProfileFragment
import com.example.myplayandroid.project.ProjectFragment

abstract class BaseHomeBottomTabWidget @JvmOverloads constructor (
    context: Context?,
    attrs: AttributeSet?=null,
    defStyleAttr: Int=0
): LinearLayout(context,attrs,defStyleAttr){

    //碎片管理器
    private var mFragmentManager: FragmentManager?=null
    //存放 Fragment 对象的可变列表
    private var mFragments = ArrayList<Fragment>()
    //记录当前显示的 Fragment
    private var currentFragment: Fragment?=null
    //持有 ViewModel 引用
    private lateinit var mViewModel: MainViewModel

    //定义了四个 Fragment 类型的成员变量
    //by lazy 委托，只有在第一次访问时才会创建 Fragment 实例，节省内存
    //每个 Fragment 实例只有在第一次被访问时才会创建（执行 newInstance()）
    private val mHomeFragment: HomePageFragment by lazy { HomePageFragment.newInstance() }
    private val mProjectFragment: ProjectFragment by lazy { ProjectFragment.newInstance() }
    private val mObjectListFragment: OfficialAccountsFragment by lazy { OfficialAccountsFragment.newInstance(
    ) }
    private val mProfileFragment: ProfileFragment by lazy { ProfileFragment.newInstance() }

    //将外部的 FragmentManager 和 MainViewModel 保存到成员变量，供后续操作使用
    //填充了四个 Fragment 实例

    //控件本身不应该持有FragmentManager，viewModel，单一职责（后续补充）
    //init方法在MainActivity可交互时执行
    fun init(fm: FragmentManager?, viewModel: MainViewModel){//init的作用，初始化当前控件
        mFragmentManager=fm
        mViewModel = viewModel

        //为什么要用if?   避免重复创建Fragment
        //mFragments： 存放 Fragment 对象的可变列表
        if (mFragments.isEmpty()){//如果mFragments列表是空的，！！重要
            mFragments.apply {
                //使用上面的自定义方法getCurrentFragment
                add(getCurrentFragment(0))
                add(getCurrentFragment(1))
                add(getCurrentFragment(2))
                add(getCurrentFragment(3))
            }
        }
        //！！重要
        //如果mFragments列表不是空的

        //从ViewModel 中读取上次保存的位置（若为 null 则默认 0）
        //并切换到该页面，使应用显示上次离开时的页面（viewModel作用）
        fragmentManger(viewModel.getPage()?:0)
        //fragmentManger下方自定义的方法
        //比如，0，1，2，3都添加了一次，现在跳到2，则获取的页数是2
        //先隐藏2的fragment,如果2没有被添加，就添加，如果添加了就直接展示
    }

    //销毁
    open fun destroy(){
        mFragmentManager?.apply {
            if (!isDestroyed)
                mFragmentManager=null
        }
        if (mFragments.isNotEmpty()){
            mFragments.clear()
        }
    }


    //！！这个方法很重要
    //Fragment 切换控制器
    //protected 允许子类重写
    protected open fun fragmentManger(position: Int){
        //position 对应底部导航栏的 Tab 索引（0: 首页, 1: 项目, 2: 公众号, 3: 我的）

        mViewModel.setPage(position)//保存当前选中的位置到 ViewModel

        //定义targetFg，用于获取目标 Fragment
        //mFragments[position]从 mFragments 列表中根据 position（整数索引）获取对应元素
        val targetFg: Fragment=mFragments[position]
        mFragmentManager?.beginTransaction()?.apply {
//             currentFragment ：记录当前显示的 Fragment
            currentFragment?.apply{
                hide(this)//隐藏当前 Fragment
            }
            //对事务中的操作进行重新排序和合并
            //例如将多个 add/hide/show 优化为最少的操作次数，提升执行效率
            setReorderingAllowed(true)

            if (!targetFg.isAdded){//如果目标 Fragment没有被添加
                //执行添加操作，添加到R.id.flHomeFragment容器
                add(R.id.flHomeFragment,targetFg).commit()
            }else{
              //将之前被 hide() 隐藏的 Fragment 重新显示出来
                show(targetFg).commit()
            }

            //！！重要
            //更新当前Fragment
            //逻辑标记，确保下次隐藏操作能找到正确的对象
            //如果没有这一行，Fragment添加过一轮以后就不会切换了
            currentFragment=targetFg
        }
    }

    //根据索引返回对应的Fragment实例
    private fun getCurrentFragment(index: Int): Fragment{
        return when (index){
            0->mHomeFragment
            1->mProjectFragment
            2->mObjectListFragment
            3->mProfileFragment
            else -> mHomeFragment
        }
    }
}
