plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    //Room
    id ("kotlin-kapt")// Kotlin 项目必须
}

android {
    namespace = "com.example.model"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
}

//根本原因是你的项目里同时引入了两个不同版本的 annotations 库：com.intellij:annotations:12.0 和 org.jetbrains:annotations:23.0.0。它们包含了相同的类，导致构建失败。
// 在 android 块外面添加
//样，所有依赖项都不会再传递 com.intellij:annotations 库，从而解决了冲突。
configurations {
    all {
        exclude(group = "com.intellij", module = "annotations")
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    //引入模块
    implementation(project(":core"))
    //Room
    implementation(libs.room.ktx)
    implementation(libs.room.runtime)
    kapt(libs.room.compiler) //标注记录

//    debugImplementation()

//    implementation(libs.room.compiler) //引入方式错误
//    kapt ("androidx.room:room-compiler:2.6.1")//没有统一版本管理

    //记录区别：
    //  kapt()，  debugImplementation(),implementation(),


}