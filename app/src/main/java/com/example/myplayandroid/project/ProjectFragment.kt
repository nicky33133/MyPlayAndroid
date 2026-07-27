package com.example.myplayandroid.project

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myplayandroid.databinding.FragmentHomePageBinding
import com.example.myplayandroid.databinding.FragmentProjectBinding


class ProjectFragment: Fragment() {
    companion object{
        @JvmStatic
        fun newInstance()= ProjectFragment()
    }

    private var binding: FragmentProjectBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentProjectBinding.inflate(inflater, container, false)
        return binding?.root  // 返回根视图
    }
}