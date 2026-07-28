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
import com.example.myplayandroid.main.MainActivity

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
    private fun initAnimation(){//构建和播放动画
//        //旋转动画
//        //让图片的中心像陀螺一样，原地转一整圈
//        val rotateAnimation = RotateAnimation(
//            // 从 0度 旋转到 360度（转一整圈）
//            0f, 360f,
//            // X轴参考点：相对于自身，0.5 代表正中心
//            Animation.RELATIVE_TO_SELF, 0.5f,
//            // Y轴参考点：相对于自身，0.5 代表正中心
//            Animation.RELATIVE_TO_SELF, 0.5f
//        )
//        rotateAnimation.duration = animationTime// 持续时长（比如 500毫秒）
//        rotateAnimation.fillAfter = true // 动画结束后，停在最终位置（360度）

//       //缩放动画
//        //图片从“一个看不见的针尖大小”，逐渐“放大”到正常尺寸（像炸开的效果）
//        val scaleAnimation = ScaleAnimation(
//            // 宽度：从 0（完全缩小没影了）变到 1（原始大小）
//            0f, 1f,
//            // 高度：从 0 变到 1
//            0f, 1f,
//            // 缩放中心：横向正中间
//            Animation.RELATIVE_TO_SELF, 0.5f,
//            // 缩放中心：纵向正中间
//            Animation.RELATIVE_TO_SELF, 0.5f
//        )
//        scaleAnimation.duration = animationTime
//        scaleAnimation.fillAfter = true


        //透明度动画 (AlphaAnimation)
        //从完全透明(0)到完全不透明(1)
        val alphaAnimation = AlphaAnimation(0f, 1f)
        // 持续时长（比如 500毫秒）
        alphaAnimation.duration = animationTime
        // 动画结束后，停在最终位置（360度）
        alphaAnimation.fillAfter = true

        //动画集合 (AnimationSet)
        // true 表示所有子动画共享同一个插值器（速度曲线）
        //插值器（Interpolator），控制的是动画播放的“节奏”或“速度曲线”
        //“里面所有的子动画（旋转、缩放、透明度），都给我使用同一条速度曲线来播放。”
        val animationSet = AnimationSet(true)
        //添加了动画
        animationSet.addAnimation(alphaAnimation)
//        animationSet.addAnimation(rotateAnimation)
//        animationSet.addAnimation(scaleAnimation)
        // 开始播放
        binding.ivWelcomeBg.startAnimation(animationSet)
        // 设置监听
        animationSet.setAnimationListener(animationListener)
    }

    override fun initData() {
        TODO("Not yet implemented")
    }

    override fun initView() {
        initAnimation()//动画初始化
        binding.ivWelcomeBg.setOnClickListener (this)
        //视图点击事件
    }

    //自定义有关动画的监听方法
    private val animationListener=object : Animation.AnimationListener{
        // 1. 动画开始时触发
        override fun onAnimationStart(animation: Animation?) {}
        // 2. 动画结束时触发（最关键的方法）
        override fun onAnimationEnd(animation: Animation?) {
            jump()//跳转到下一个界面
        }
        // 3. 动画重复时触发
        override fun onAnimationRepeat(animation: Animation?) {}
    }

    //自定义跳转方法
    private fun jump(){
        MainActivity.actionStart(this)
        finish() //销毁欢迎页
    }

    override fun onClick(v: View?) {//点击
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


    //判断按键事件
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode== KeyEvent.KEYCODE_BACK){//判断是否为返回键
            exit()//使用自定义方法
            return false//返回 false，表示未完全消费该事件
            //（事件继续往下传）。系统或父控件会接着处理这个事件。
        }
        return super.onKeyDown(keyCode, event)//其他按键交给系统处理
    }


}