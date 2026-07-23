package com.example.myplayandroid.view.base.Ice

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
//import com.example.myplayandroid.BaseFragmentInit
import com.example.myplayandroid.DefaultLceImpl
import com.example.myplayandroid.R
import com.example.myplayandroid.showToast
import com.example.myplayandroid.view.base.Ice.ILce
import com.scwang.smart.refresh.layout.util.SmartUtil.dp2px

//抽象方法
//所有Fragment的基类
abstract class BaseFragment : Fragment(), ILce, BaseFragmentInit {

    private var defaultLce: ILce? = null
    private var loadErrorView: View? = null
    private var badNetworkView: View? = null
    private var noContentView: View? = null
    private var loading: View? = null


    //protected：访问修饰符。表示该函数对当前类及其子类可见
    protected open fun isHaveHeadMargin(): Boolean{
        return true
    }


    //布局叠加
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //部分一：创建容器和View
        //创建一个容器，该容器是FrameLayout的类型，FrameLayout实现简单，性能最高
        val frameLayout=FrameLayout(requireContext())
        //BaseFragment是所有Fragment的基类，引入跟布局layout_lce
        val lce= View.inflate(context, R.layout.layout_lce,null)
        //创建 FrameLayout.LayoutParams
        //作用是：往 FrameLayout 里放一个东西，这个东西的宽和高都强行跟父容器（FrameLayout）一样大
        //可以通过代码的方式设置边距
        val params= FrameLayout.LayoutParams(
            //可以通过代码的方式设置边距
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.MATCH_PARENT)
        //获取当前屏幕方向（isPort）
        val isPort=resources.configuration.orientation== Configuration.ORIENTATION_PORTRAIT


        //部分二:设置view的边距
        params.setMargins(//setMargins设置边距
            0,
            //只有“上边距（Top）” 是动态计算的
            if (isHaveHeadMargin()) {
                //dp2px将dp值转换为像素值
                dp2px(if (isPort) 70f else 55f)
                //竖屏（Portrait）：使用 70f dp。
                //横屏（Landscape）：使用 55f dp
            } else 0,
            0,
            0
        )
        lce.layoutParams=params


        //部分三：往容器里添加view
        //这一行是容器
        val content=getLayoutView(inflater,container,false)
        //下面是往容器里添加View
        frameLayout.addView(content)//内容view
        frameLayout.addView(lce)//状态view
        onCreateView(lce)

        //部分四：返回容器
        return frameLayout//返回frameLayout这个容器
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()//初始化子类自己的 UI 控件
        initData()//初始化数据
    }

    //作用：把“加载中 → 显示内容 → 显示错误”的状态切换代码全部封装起来
    //只需要告诉它“数据来了该怎么显示”即可
    //设置 LiveData 的状态，根据不同状态显示不同页面
    fun <T>setDataStatus(
        dataLiveData: LiveData<Result<T>>,// 1. 数据源
        onBadNetwork:()-> Unit={},// 2. 失败时的额外操作（可选）
        onDataStatus:(T)-> Unit // 3. 成功时处理数据的操作（必填）
    ){
        //观察 dataLiveData
        dataLiveData.observe(this){//this指的是当前的LifecycleOwner（即Fragment或Activity）
            if (it.isSuccess){
                //如果 Result 是成功状态，getOrNull() 返回实际的数据 T；
                // 如果成功但数据本身就是 null，则返回 null
                val dataList=it.getOrNull()

                //判空
                if (dataList!=null){
                    loadFinished()
                    onDataStatus(dataList)// 把数据传给调用者，让调用者更新 UI
                }else{
                    showLoadErrorView()
                }
            }else{
                context?.showToast(getString(R.string.bad_network_view_tip))
                showBadNetworkView { initData() }
                onBadNetwork.invoke()
            }
        }

    }

    //这个方法的作用是：将传入的view进行处理，然后原样返回
    private fun onCreateView(view: View): View{
        loading=view.findViewById(R.id.loading)
        noContentView=view.findViewById(R.id.noContentView)
        badNetworkView=view.findViewById(R.id.badNetworkView)
        loadErrorView=view.findViewById(R.id.loadErrorView)
        //判空检查
        if (loading==null){
            throw NullPointerException("loading is null")
        }
        if (badNetworkView==null){
            throw NullPointerException("badNetworkView is null")
        }
        if (loadErrorView==null){
            throw NullPointerException("loadErrorView is null")
        }
        //构建状态管理类实例
        defaultLce= DefaultLceImpl(//DefaultLceImpl是自己写的方法
           loading,
            loadErrorView,
            badNetworkView,
            noContentView
        )
        return view//返回传入的 View
    }

    //复写接口ILce的方法，有关加载过程的
    override fun startLoading() {
        defaultLce?.startLoading()
    }

    override fun loadFinished() {
        defaultLce?.loadFinished()
    }

    override fun showLoadErrorView(tip: String) {
        defaultLce?.showLoadErrorView(tip)
    }

    override fun showBadNetworkView(listener: View.OnClickListener) {
        defaultLce?.showBadNetworkView(listener)
    }

    override fun showNoContentView(tip: String) {
        defaultLce?.showNoContentView(tip)
    }

}

