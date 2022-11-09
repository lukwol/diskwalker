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
include(":libraries:formatters")
include(":libraries:ranges")
include(":libraries:support")
