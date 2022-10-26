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
include(":libraries:viewmodel")
include(":libraries:screens-navigation")
include(":libraries:windows-navigation")
include(":libraries:viewmodel-screens-navigation")
