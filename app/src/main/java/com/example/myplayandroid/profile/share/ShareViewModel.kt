package com.example.myplayandroid.profile.share

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.example.model.room.entity.Article
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class ShareViewModel @Inject constructor(
    private  val shareRepository: ShareRepository
): ViewModel(){



    //定义一个数据列表
    //ArrayList 是 Java 集合框架中的具体类，底层基于动态数组实现。它支持通过索引（[index]）快速随机访问，但在列表中间插入/删除元素时效率较低（需要移动后续元素）。
    //<Article> 是泛型，指定这个列表只能存放 Article 类型的对象（不能放 String 或 Int
    val articleList = ArrayList<Article>()




    //定义一个（可变的 LiveData 容器）
    //泛型 <Int> 指定了它装的是整型数据。
    //调用构造器 ()，初始时内部数据为 null（因为未赋值）
    //这个对象本质上是一个“带生命周期感知能力的数据仓库”
    //LiveData是Jetpack提供的一种响应式编程组件，它可以包含任何类型的数据，并在数据发生变化的时候通知给观察者
    private val pageLiveData = MutableLiveData<Int>()

    private val pageAndCidLiveData = MutableLiveData<ShareArticle>()

    //将这个LiveData对象转换成另外一个可观察的LiveData对象
    //获取我的分享列表
    val articleLiveData = pageLiveData.switchMap { page ->
       shareRepository.getMyShareList(page)
    }
    //一旦 pageLiveData 的数据发生变化，
    // 那么观察 pageLiveData 的switchMap()方法就会执行，并且调用我们编写的转换函数。
     // 然后在转换函数中调用shareRepository.getMyShareList(page)方法获取真正的用户数据。


    //获取分享列表
    val articleAndCidLiveData = pageAndCidLiveData.switchMap { page->
        shareRepository.getShareList(page.cid,page.page)
    }


    //供外部调用
    //设置pageLiveData的值，也就是pageLiveData数据改变的地方
    fun getArticleListMine(page: Int){
        pageLiveData.value=page
    }

    fun getArticleList(cid: Int,page: Int){
        pageAndCidLiveData.value= ShareArticle(cid,page)
    }

}

data class ShareArticle(var cid: Int,var page: Int)