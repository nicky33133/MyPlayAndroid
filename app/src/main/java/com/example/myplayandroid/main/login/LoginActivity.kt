package com.example.myplayandroid.main.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.core.view.base.lce.BaseActivity
import com.example.myplayandroid.R
import com.example.myplayandroid.databinding.ActivityLoginBinding

class LoginActivity : BaseActivity(), View.OnClickListener, TextWatcher {
    lateinit var binding: ActivityLoginBinding


    override fun getLayoutView(): View {

        binding= ActivityLoginBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onClick(v: View?) {}

    override fun afterTextChanged(s: Editable?) {}

    override fun beforeTextChanged(
        s: CharSequence?,
        start: Int,
        count: Int,
        after: Int
    ) {}

    override fun onTextChanged(
        s: CharSequence?,
        start: Int,
        before: Int,
        count: Int
    ) {}


    companion object{
        fun actionStart(context: Context){
            val intent= Intent(context, LoginActivity::class.java)
            context.startActivity(intent)
        }
    }
}