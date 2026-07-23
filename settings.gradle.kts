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
 