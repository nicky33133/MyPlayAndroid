package com.example.myplayandroid

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
//import androidx.fragment.app.viewModels
import com.example.myplayandroid.databinding.FragmentHomePageBinding
import com.youth.banner.transformer.DepthPageTransformer
import com.youth.banner.transformer.ZoomOutPageTransformer

//class HomePageFragment: ArticleCollectBaseFragment(){
//    private val viewModel by viewModels<Home>()
//    private var binding: FragmentHomePageBinding?=null
//
//
//    override fun getLayoutView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        attachToRoot: Boolean
//    ): View {
//      binding= FragmentHomePageBinding.inflate(inflater,container,attachToRoot)
//        return binding!!.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        binding?.apply {
//            homeBanner.addBannerLifecycleObserver(viewLifecycleOwner)
//            homeBanner2.addBannerLifecycleObserver(viewLifecycleOwner)
//            homeBanner.setPageTransformer(ZoomOutPageTransformer())
//            homeBanner2.setPageTransformer(DepthPageTransformer())
//
//        }
//    }
//}