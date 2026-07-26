pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
        // 添加阿里云镜像
        maven (url ="https://maven.aliyun.com/repository/public" )
        maven ( url ="https://maven.aliyun.com/repository/google")
        maven (url = "https://maven.aliyun.com/repository/jcenter")
    }

    plugins {
        // 指定 KSP 插件的版本
        id("com.google.devtools.ksp") version "2.0.0-1.0.21"  // 注意版本号要与你的 Kotlin 版本匹配
        // 如果还有 Hilt 插件，也可以一并声明
        id("com.google.dagger.hilt.android") version "2.57.1"
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        // 添加阿里云镜像
        maven (url ="https://maven.aliyun.com/repository/public" )
        maven ( url ="https://maven.aliyun.com/repository/google")
        maven (url = "https://maven.aliyun.com/repository/jcenter")
    }
}

rootProject.name = "MyPlayAndroid"
include(":app")
include(":core")
include(":model")
include(":network")
