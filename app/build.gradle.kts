plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
//    alias(libs.plugins.ksp)
    //Hilt
//    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.example.myplayandroid"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.example.myplayandroid"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        //Bugly
        ndk {
            // 设置支持的SO库架构
            abiFilters.add("armeabi-v7a")  //, 'x86', 'armeabi-v7a', 'x86_64', 'arm64-v8a'
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        viewBinding = true
    }
//    repositories {
//        maven (url = "https://s01.oss.sonatype.org/content/groups/public")
//    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    //Lottie 开源动画库
    implementation("com.airbnb.android:lottie:6.7.0")
    //Banner库
//    implementation("com.youth.banner:banner:2.2.2")
//    implementation("io.github.youth5201314:banner:2.2.3")
    implementation("com.youth.banner:banner:2.1.0")
    // 图片加载库（推荐Glide）
    implementation("com.github.bumptech.glide:glide:4.12.0")
    //viewBinding
    implementation("com.android.databinding:viewbinding:7.2.2")
    //SmartRefreshLayout
    implementation("io.github.scwang90:refresh-layout-kernel:2.1.0")
    //    implementation("com.scwang.smartrefresh:SmartRefreshLayout:1.1.0")
    // 经典的 Header 和 Footer (可选，但强烈推荐)
    implementation("io.github.scwang90:refresh-header-classics:2.1.0")
    //UtilCode工具集
    implementation("com.blankj:utilcodex:1.31.1")
    // ViewModel 核心库（必须）
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1")
    // 为 Activity 提供 by viewModels() 扩展（如需在Activity中使用）
    implementation("androidx.activity:activity-ktx:1.7.1")
    // 为 Fragment 提供 by viewModels() 扩展（如需在Fragment中使用）
    implementation("androidx.fragment:fragment-ktx:1.6.1")
    // Hilt 核心库
//    implementation("com.google.dagger:hilt-android:2.57.1")
    // Hilt 注解处理器（用 ksp）
//    ksp("com.google.dagger:hilt-android-compiler:2.57.1")
    // LiveData
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.8.4")
    //引入模块
    implementation(project(":model"))
    //引入core模块
    implementation(project(":core"))
    implementation(project(":network"))
    //Bugly
    implementation("com.tencent.bugly:crashreport:4.1.9.3")
    // SmartRefreshLayout 核心库
    implementation("com.scwang.smartrefresh:SmartRefreshLayout:1.1.3")
    // 包含 ClassicsHeader 等内置 Header
    implementation("com.scwang.smartrefresh:SmartRefreshHeader:1.1.3")
    //MMKV
    //官方提示，直接使用 1.3.x 的最新稳定版，它完美支持 32 位和 64 位
    implementation("com.tencent:mmkv:1.3.4")
    //BaseQuickAdapter 是开源库 BRVAH (BaseRecyclerViewAdapterHelper) 中的核心类
    implementation ("com.github.CymChad:BaseRecyclerViewAdapterHelper:3.0.4")


}