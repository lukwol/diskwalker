pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
}

rootProject.name = "DiskUsage"

include(":app")
include(":data")
include(":domain")
include(":presentation")
include(":libraries:core")
include(":libraries:support")
