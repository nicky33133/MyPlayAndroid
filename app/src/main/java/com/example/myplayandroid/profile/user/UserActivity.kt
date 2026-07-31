package com.example.myplayandroid.profile.user

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.core.view.base.lce.BaseActivity
import com.example.myplayandroid.R
import com.example.myplayandroid.databinding.ActivityUserBinding

class UserActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
    }
    override fun getLayoutView(): View {
        return ActivityUserBinding.inflate(layoutInflater).root
    }
    override fun initView() {}
    override fun initData() {}

    companion object{
        fun actionStart(context: Context){
            val intent= Intent(context, UserActivity::class.java)
            context.startActivity(intent)
        }
    }
}