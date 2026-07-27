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
): LinearLayout(context,attrs,defStyleAttr), View.OnClickListener {

//    init {
//        orientation = VERTICAL
//    }

    private var mFragmentManager: FragmentManager?=null
    //放fragment的列表
    private var mFragments = ArrayList<Fragment>()
    private var currentFragment: Fragment?=null
    private lateinit var mViewModel: MainViewModel


    //by lazy 委托，只有在第一次访问时才会创建 Fragment 实例，节省内存
    private val mHomeFragment: HomePageFragment by lazy { HomePageFragment.newInstance() }
    private val mProjectFragment: ProjectFragment by lazy { ProjectFragment.newInstance() }
    private val mObjectListFragment: OfficialAccountsFragment by lazy { OfficialAccountsFragment.newInstance() }
    private val mProfileFragment: ProfileFragment by lazy { ProfileFragment.newInstance() }


    //根据索引返回对应的 Fragment 实例
    private fun getCurrentFragment(index: Int): Fragment{
        return when (index){
            0->mHomeFragment
            1->mProjectFragment
            2->mObjectListFragment
            3->mProfileFragment
            else -> mHomeFragment
        } as Fragment//as Fragment 强制类型转换：将 when 的返回值强制转为 Fragment 类型
    }

//    //初始化
//    //填充了四个 Fragment 实例
    fun init(fm: FragmentManager?, viewModel: MainViewModel){
        mFragmentManager=fm
        mViewModel = viewModel
        if (mFragments.isEmpty()){
            mFragments.apply {
                add(getCurrentFragment(0))
                add(getCurrentFragment(1))
                add(getCurrentFragment(2))
                add(getCurrentFragment(3))
            }
        }
        fragmentManger(viewModel.getPage()?:0)
    }


    //Fragment 切换控制器
    //protected 允许子类重写
    protected open fun fragmentManger(position: Int){
        //position 对应底部导航栏的 Tab 索引（0: 首页, 1: 项目, 2: 公众号, 3: 我的）

        mViewModel.setPage(position)

        //获取目标 Fragment
        val targetFg: Fragment=mFragments[position]
        mFragmentManager?.beginTransaction()?.apply {
            currentFragment?.apply{
                hide(this)//隐藏当前 Fragment
            }

            //对事务中的操作进行重新排序和合并
            //例如将多个 add/hide/show 优化为最少的操作次数，提升执行效率
            setReorderingAllowed(true)
            if (!targetFg.isAdded){
                add(R.id.flHomeFragment,targetFg).commit()
            }else{
                show(targetFg).commit()
            }
            currentFragment=targetFg
        }
    }

    open fun destroy(){
        mFragmentManager?.apply {
            if (!isDestroyed)
                mFragmentManager=null
        }
        if (mFragments.isNotEmpty()){
            mFragments.clear()
        }
    }
}
