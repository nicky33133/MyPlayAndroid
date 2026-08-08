package com.example.core.view.custom

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter


//适配器基类
class FragmentAdapter(
    mFragmentManager: FragmentManager,lifecycle: Lifecycle
):FragmentStateAdapter(mFragmentManager,lifecycle) {
    private val mFragment: MutableList<Fragment> = ArrayList()
    private lateinit var mTitles: Array<String>

    //更新 Fragment 列表
    fun resetFragment(fragments: List<Fragment>?){
        fragments?.apply {
            mFragment.clear()
            mFragment.addAll(this)
        }

    }

    fun title(position: Int): String{
        return mTitles[position]
    }

    override fun createFragment(position: Int): Fragment {
       return mFragment[position]
    }

    override fun getItemCount(): Int {
        return mFragment.size
    }

    //更新标题数组
    //有更改Array<String>-ArrayList<String>
    fun resetTitles(titles: Array<String>) {
        //类型匹配错误，记录
        mTitles = titles
//        Assignment type mismatch: actual type is 'Array<String>',（给的值）
//     but 'ArrayList<String>' was expected.（等待被赋值的数）

        //titles是传入的参数（给的值）
        //mTitles的值取决于传入参数的值
        //mTitles本来是一个没有值的（等待被赋值的数）
    }

}