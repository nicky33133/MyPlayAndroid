package com.example.model.model

data class BaseModel<T>(
    val `data`:T,//data是kotlin的关键词,不能直接当参数名，所以需要两个上标
    //用Kotlin的保留关键字（比如 is、class、object）作为变量名或函数名时，
    // 用反引号包起来告诉编译器“这是一个名字，不是关键字”。
    val errorCode: Int,
    val errorMsg: String
    )
