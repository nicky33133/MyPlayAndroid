package com.example.myplayandroid.home.search

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.core.util.showToast
import com.example.core.view.base.lce.BaseActivity
import com.example.model.model.ArticleList
import com.example.model.room.PlayDatabase
import com.example.model.room.dao.HotKeyDao
import com.example.model.room.entity.HotKey
import com.example.myplayandroid.R
import com.example.myplayandroid.databinding.ActivitySearchBinding
import com.example.myplayandroid.home.article.ArticleListActivity
import com.example.myplayandroid.home.article.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class SearchActivity : BaseActivity(), View.OnClickListener, TextView.OnEditorActionListener {

    private lateinit var hotKeyDao: HotKeyDao
    private lateinit var binding: ActivitySearchBinding

    private val viewModel by viewModels<SearchViewModel>()


//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
////        enableEdgeToEdge()
//    }

    companion object {
        fun actionStart(context: Context) {
            val intent = Intent(context, SearchActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun getLayoutView(): View {
        binding = ActivitySearchBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun initView() {
        binding.apply {
            //setOnClickListener中要传入OnClickListener类型的参数，this@SearchActivity当前Activity是继承了View.OnClickListener的，所以可以直接传入当前Activity
            //   public void setOnClickListener(@Nullable OnClickListener l) {
            //        if (!isClickable()) {
            //            setClickable(true);
            //        }
            //        getListenerInfo().mOnClickListener = l;
            //    }

            searchImgBack.setOnClickListener(this@SearchActivity)
            searchTxtRight.setOnClickListener(this@SearchActivity)
            searchTxtKeyword.setOnEditorActionListener(this@SearchActivity)
        }
    }

    //    加载数据
    override fun initData() {
        viewModel.getDataList(true)
        hotKeyDao = PlayDatabase.getDatabase(this).hotKeyDao()
        //如果观测数据失败
        setDataStatus(viewModel.dataLiveData) {
            if (it.isNotEmpty() && viewModel.dataList.isEmpty()) {
                viewModel.dataList.clear()
                viewModel.dataList.addAll(it)
            }
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.searchImgBack -> {//返回图标
                finish()
                overridePendingTransition(0, R.anim.search_push_out)
            }

            R.id.searchTxtRight -> {
                toSearch()
            }
        }
    }

    private fun toSearch() {
        //引入搜索页布局文件
        //提取用户输入的关键词
        val keyword = binding.searchTxtKeyword.text.toString()
        //搜索词不为空
        if (TextUtils.isEmpty(keyword)) {
            showToast(getString(R.string.keyword_not_null))
            return
        }
        //将 id 设为 -1 是一种常见约定，表示这是一个新记录
        //未持久化），插入数据库时由数据库自动分配真实 ID
        val hotKey = HotKey(id = -1, name = keyword)
        //协程
        lifecycleScope.launch {
            //将搜索词异步写入数据库（通常用于记录搜索历史或热词统计）
            hotKeyDao.insert(hotKey)
        }
        //将新的 hotKey 插入到列表的 第 0 位（头部），
        // 使最新搜索词显示在最前面，通常用于搜索历史界面实时刷新
        viewModel.dataList.add(0, hotKey)

        ArticleListActivity.actionStart(this, keyword)//根据关键词，打开搜索内容页

    }


    //监听软键盘上的操作按钮（如“搜索”、“发送”、“完成”等）或物理键盘的按下事件
    override fun onEditorAction(
        v: TextView?,
        actionId: Int,
        event: KeyEvent?
    ): Boolean {
        //如果 event 为 null，表示该事件是由软键盘操作按钮（如点击“搜索”）触发的，而非物理按键
        if (event == null) return false
        //当 动作 ID 为发送（IME_ACTION_SEND） 或 物理按键为回车键（KEYCODE_ENTER） 时，进入处理块
        return if (actionId == EditorInfo.IME_ACTION_SEND || event.keyCode == KeyEvent.KEYCODE_ENTER) {
            when (event.action) {//event.action 表示按键的动作类型
                KeyEvent.ACTION_UP -> {//按键弹起
                    toSearch()
                    true//返回 true 表示已处理
                }
                //返回 false，表示不处理该事件，交给系统或父类处理。
                else -> false
            }
        } else false
    }
}