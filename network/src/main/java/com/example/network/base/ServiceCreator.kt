package com.example.network.base

import okhttp3.Call
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ServiceCreator {

    private const val CONNECT_TIMEOUT = 30L
    private const val READ_TIMEOUT = 10L
    private const val BASE_URL = "https://www.wanandroid.com/"

    // 1. 创建日志拦截器，设置日志级别
    val loggingInterceptor = HttpLoggingInterceptor().apply {
        // 开发阶段使用 Level.BODY，发布前改为 Level.BASIC 或 NONE
        level = HttpLoggingInterceptor.Level.BODY
    }


    private val cookieJar = object : CookieJar {
        private val cookieStore = mutableMapOf<String, MutableList<Cookie>>()


        override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
            val host = url.host
            cookieStore.getOrPut(host) { mutableListOf() }.apply {
                removeAll { cookie -> cookies.any { it.name == cookie.name } }
                addAll(cookies)
            }
        }

        override fun loadForRequest(url: HttpUrl): List<Cookie> {
            val hostCookies = cookieStore[url.host] ?: return emptyList()
            return hostCookies.filter { it.matches(url) }
        }

        fun clear() {
            cookieStore.clear()
        }

        fun hasLoginCookie(): Boolean =
            cookieStore.values.any { cookies -> cookies.any { it.name == "loginUser" } }
    }





    //供外部调用的方法
    //第二个 create(service)：调用Retrofit对象的create(Class<T> service)方法，
    //该方法在运行时会动态生成该接口的代理对象，使得调用接口方法时自动转化为 HTTP 请求
    fun <T> create(service: Class<T>): T = create1().create(service) //后面这个create()是系统的方法

    //私有的工厂方法，用于创建并配置一个 Retrofit 网络请求客户端实例
    private fun create1(): Retrofit {//返回值类型是 Retrofit 对象
        return RetrofitBuild(//RetrofitBuild一个构建器类，用于封装 Retrofit 的配置参数
            url = BASE_URL,//API 的基础地址
            client = okHttpClient,//一个预先配置好的 OkHttpClient 实例
            //创建一个 Gson 转换器工厂,转换数据的
            gsonFactory = GsonConverterFactory.create()
        ).retrofit//从 RetrofitBuild 对象中取出最终构建好的 Retrofit 实例
    }

    //封装 Retrofit 实例的构建过程
    class RetrofitBuild(
        //	指定 API 的基础 URL//自定义的 OkHttp 客户端
        url: String, client: OkHttpClient,
        //数据转换器工厂
        gsonFactory: GsonConverterFactory
    ) {
        val retrofit: Retrofit = Retrofit.Builder().apply {
            baseUrl(url)  // ① 设置 Base URL
            client(client) // ② 注入 OkHttpClient
            addConverterFactory(gsonFactory)// ③ 添加 JSON 转换器
        }.build() // ④ 调用 build() 创建 Retrofit 实例
    }

    //kHttpClient 单例构建 实现，结合了 lazy 委托和自定义拦截器
    // 用于在 Android 应用中自动管理 Cookie（会话保持）
    // 它通常与 Retrofit 配合使用，作为网络请求的底层客户端
    private val okHttpClient by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
        //加拦截器
        OkHttpClient().newBuilder().apply {
            //val   result  =  obj.apply  {  obj的上下文  }
            // result == obj

            //连接超时时间
            connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            //读取超时时间
            readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            //添加拦截器
            addInterceptor(loggingInterceptor)

            cookieJar(cookieJar)           // ✅ 新增：启用 Cookie 管理
        }.build()
    }
}
