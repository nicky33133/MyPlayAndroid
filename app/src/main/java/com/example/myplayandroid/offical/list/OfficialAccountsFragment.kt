package com.example.myplayandroid.offical.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myplayandroid.databinding.FragmentOfficialAccountsBinding
import com.example.myplayandroid.databinding.FragmentProjectBinding

class OfficialAccountsFragment: Fragment() {
    companion object{
        @JvmStatic
        fun newInstance()= OfficialAccountsFragment()
    }
    private var binding: FragmentOfficialAccountsBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentOfficialAccountsBinding.inflate(inflater, container, false)
        return binding?.root  // 返回根视图
    }
}