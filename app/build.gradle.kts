plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
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

}