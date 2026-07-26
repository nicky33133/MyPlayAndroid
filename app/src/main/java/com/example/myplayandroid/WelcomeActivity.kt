package com.example.myplayandroid

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.RotateAnimation
import android.view.animation.ScaleAnimation
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.core.view.base.lce.BaseActivity
import com.example.myplayandroid.databinding.ActivityWelcomeBinding

class WelcomeActivity : BaseActivity(), View.OnClickListener{
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        setContentView(R.layout.activity_welcome)
//
//    }
    private lateinit var binding: ActivityWelcomeBinding
    private var exitTime: Long=0
    private var animationTime: Long=500

    override fun getLayoutView(): View {
        binding= ActivityWelcomeBinding.inflate(layoutInflater)
        return binding.root
    }

    //自定义动画的
    private fun initAnimation(){
        val rotateAnimation = RotateAnimation(
            0f, 360f,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
        )
        rotateAnimation.duration = animationTime
        rotateAnimation.fillAfter = true
        val scaleAnimation = ScaleAnimation(
            0f, 1f, 0f, 1f,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
        )
        scaleAnimation.duration = animationTime
        scaleAnimation.fillAfter = true
        val alphaAnimation = AlphaAnimation(0f, 1f)
        alphaAnimation.duration = animationTime
        alphaAnimation.fillAfter = true
        val animationSet = AnimationSet(true)
        animationSet.addAnimation(alphaAnimation)
        binding.ivWelcomeBg.startAnimation(animationSet)

        animationSet.setAnimationListener(animationListener)
    }

    override fun initView() {
        initAnimation()//使用自定义动画的
        binding.ivWelcomeBg.setOnClickListener (this)
    }

    //自定义有关动画的方法
    private val animationListener=object : Animation.AnimationListener{
        override fun onAnimationStart(animation: Animation?) {
        }

        override fun onAnimationEnd(animation: Animation?) {
            jump()//跳转到登陆界面
        }

        override fun onAnimationRepeat(animation: Animation?) {
        }
    }

    //自定义跳转方法
    private fun jump(){
        MainActivity.actionStart(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.ivWelcomeBg->{
                jump()//使用自定义跳转方法
            }
        }
    }

    //自定义方法，双击返回键退出应用
    private fun exit(){
        //System.currentTimeMillis()：获取当前系统时间戳（毫秒）
        //计算当前时间与上一次记录的时间之差，是否 大于 2000 毫秒（2 秒）
        if (System.currentTimeMillis()-exitTime>2000){
            showToast(R.string.exit_program)
            //将 exitTime 更新为当前时间，以便下一次点击时重新计时
            exitTime= System.currentTimeMillis()
        }else{
            finish()//结束当前 Activity
        }
    }
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode== KeyEvent.KEYCODE_BACK){//判断是否为返回键
            exit()//使用自定义方法
            return false//返回 false，表示未完全消费该事件
        }
        return super.onKeyDown(keyCode, event)//其他按键交给系统处理
    }
}