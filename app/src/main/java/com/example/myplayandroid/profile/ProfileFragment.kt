package com.example.myplayandroid.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myplayandroid.databinding.FragmentOfficialAccountsBinding
import com.example.myplayandroid.databinding.FragmentProfileBinding
import com.example.myplayandroid.project.ProjectFragment

class ProfileFragment : Fragment(){
    companion object{
        @JvmStatic
        fun newInstance()= ProfileFragment()
    }
    private var binding: FragmentProfileBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=  FragmentProfileBinding.inflate(inflater, container, false)
        return binding?.root  // 返回根视图
    }
}