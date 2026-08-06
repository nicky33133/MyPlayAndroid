package com.example.myplayandroid.base

import android.util.Log
import androidx.lifecycle.liveData
import com.example.model.model.BaseModel

//作用：AndroidX LiveData 协程构建器 的封装，
// 用于简化在ViewModel或Repository中通过协程获取数据并发射 结果状态（成功/失败）的逻辑。
// 它们在项目中充当了数据层与 UI 层之间的桥梁，统一了异步数据处理的错误处理和结果封装

private const val TAG="LiveDataUtils"

//作用：将“可能失败的网络请求”统一转化为 Result 类型，并封装为 LiveData
//BaseModel一个数据类
fun <T> liveDataModel(block: suspend () -> BaseModel<T>) =
    //参数 block：一个挂起函数（suspend），执行时会返回 BaseModel<T>
    //LiveData作用 可以包含任何类型的数据，并在数据发生变化的时候通知给观察者
    liveData {
        val result=try {
            val baseModel=block()//block()是方法
            if (baseModel.errorCode==0){
                val model =baseModel.data//若成功，取出 data
                Result.success(model)//包装成功结果
            }else{//否则失败的操作
                Log.e(TAG,
                    "fires: response status is${baseModel.errorCode} msg is ${baseModel.errorMsg}"
                )
                Result.failure(RuntimeException(baseModel.errorMsg))

            }
        }catch (e: Exception){//捕捉异常
            Log.e(TAG,e.toString())
            Result.failure(e)
        }
        emit(result)// 将 Result<T> 发射出去
    }


//作用：当调用方已经拥有Result 类型的结果时，
// 只需将其丢给 LiveData 发射，并自动捕获异常
fun <T> liveDataFire(block:suspend () -> Result<T>)=
    //返回值类型是 Result<T>（Kotlin 标准库中的 Result 类）
    //suspend () -> Result<T>：这是一个挂起函数类型的入参
    liveData{
        // liveData { ... } 构建器（来自 lifecycle-livedata-ktx），
        // 它会自动创建一个 LiveData 对象，并为它开启一个协程作用域
        val result=try {
            block()//尝试执行 block()，若成功则得到 Result<T>
        }catch (e: Exception){
            Log.e(TAG,"fire $e")

            //统一错误处理
            Result.failure(e)//异常情况下的返回值
        }
        emit(result)//将最终 Result 发射出去
    }