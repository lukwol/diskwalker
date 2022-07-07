pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
}

rootProject.name = "DiskUsage"

enableFeaturePreview("VERSION_CATALOGS")

include(":app")
include(":data")
include(":domain")
include(":presentation")
include(":libraries:support")
include(":libraries:ranges")
