package com.example.myplayandroid

import android.app.Application
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.tencent.bugly.crashreport.CrashReport
//import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

//@HiltAndroidApp
//@HiltAndroidApp：这是 Hilt 的入口触发器。它会触发 Hilt 的注解处理器，
//生成一个继承自 Application 的基类，并负责创建全局的依赖注入容器（Component）。
//如果不加这行，项目中所有使用 @Inject 和 @AndroidEntryPoint 的地方都会编译失败
class App: Application() {
    override fun onCreate() {
        super.onCreate()//保证父类的初始化逻辑（比如系统环境准备）被正确执行
        //调用Play单例中的方法
        Play.initialize(applicationContext)//applicationContext（即全局上下文）
        initData()

    }
    private fun initData(){
        CoroutineScope(Dispatchers.IO+ SupervisorJob()).launch {
            //CoroutineScope(...)：创建一个协程作用域，用于管理协程的生命周期
            //SupervisorJob()：作用是子协程的失败不会影响到父协程或兄弟协程
            initBugLy()
        }
    }

    private fun initBugLy(){
        // Bugly bug上报
        CrashReport.initCrashReport(applicationContext,"fa4cd4ec7b",false)
        //"fa4cd4ec7b"是在 Bugly 平台申请的应用唯一标识（App ID）
        //false：表示不开启调试模式（Debug 模式）
        //如果是 true，会在 Logcat 中打印大量 SDK 内部日志，且可能增加上报频率；发布版必须设为 false
    }

    companion object{
        //companion object：Kotlin 中的伴生对象，
        //相当于Java中的static成员。它会在类的加载阶段被执行（比 onCreate 更早）
        init {//类首次加载到内存时执行且仅执行一次

            SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, layout ->
              //setDefaultRefreshHeaderCreator：设置全局的头部
                //context：当前刷新布局所在的上下文。

               //layout：当前的刷新布局对象
                layout.setPrimaryColorsId(
                    R.color.refresh,//背景色
                    R.color.text_color//文字颜色
                )
                ClassicsHeader(context)//返回经典的 Material Design 风格头部视图
            }

            SmartRefreshLayout.setDefaultRefreshFooterCreator {
                //setDefaultRefreshFooterCreator：设置全局的底部工厂
                context, _ ->
                ClassicsFooter(context).setDrawableSize(20f)//图标大小设置为 20dp
            }

        }
    }
}