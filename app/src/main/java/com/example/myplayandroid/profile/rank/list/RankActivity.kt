package com.example.myplayandroid.profile.rank.list

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.model.model.Rank
import com.example.myplayandroid.R
import com.example.myplayandroid.base.BaseListActivity

class RankActivity : BaseListActivity() {

//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        setContentView(R.layout.activity_rank)
//    }

    private  lateinit var rankAdapter: RankAdapter
    private val viewModel by viewModels<RankViewModel>()


    override fun getDataList() {

        if (viewModel.dataList.size <= 0)
            viewModel.getDataList(page)
    }


    override fun initData(){
        super.initData()
        // setDataStatus()写在BaseActivity中
        setDataStatus(viewModel.dataLiveData){
            if (page == 1 && viewModel.dataList.size > 0){
                viewModel.dataList.clear()
            }

            viewModel.dataList.addAll(it.datas)

            rankAdapter.setNewInstance(it.datas as  MutableList)
            Log.d("ddd", "initData: ${it.datas.size}")
        }
    }

    override fun initView() {
        super.initView()
        binding.baseListTitleBar.setTitle(getString(R.string.ranking_list))

        //适配器初始化
        rankAdapter= RankAdapter(this,viewModel.dataList)


//        <com.example.myplayandroid.base.ToTopRecyclerView
//        android:id="@+id/baseListToTop"
        binding.baseListToTop.setAdapter(rankAdapter)
    }

    companion object{
        fun actionStart(context: Context){
            val intent= Intent(context,RankActivity::class.java)
            context.startActivity(intent)
        }
    }
}