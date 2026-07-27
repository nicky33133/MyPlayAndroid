package com.example.myplayandroid.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myplayandroid.databinding.FragmentHomePageBinding

class HomePageFragment: Fragment() {

    companion object{
        @JvmStatic
        //加上 @JvmStatic 后，Java 代码就可以像调用静态方法一样直接写：
        // HomePageFragment.newInstance()，和原生 Java 写法的体验一致
        fun newInstance()= HomePageFragment()
        //fun newInstance() = HomePageFragment()
        // 等价于：
       // fun newInstance(): HomePageFragment {
//            return HomePageFragment()
//        }
//        它的作用是创建一个 HomePageFragment 的新实例并返回
    }

    private var binding: FragmentHomePageBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentHomePageBinding.inflate(inflater, container, false)
        return binding?.root  // 返回根视图
    }

}