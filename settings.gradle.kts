@file:Suppress("UnstableApiUsage")

rootProject.name = "DiskUsage"

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
        maven("https://jitpack.io")
    }
}

include(":app")
include(":data")
include(":domain")
include(":presentation")
include(":libraries:formatters")
include(":libraries:ranges")
include(":libraries:support")
include(":libraries:utils")

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
