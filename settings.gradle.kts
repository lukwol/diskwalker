@file:Suppress("UnstableApiUsage")

rootProject.name = "DiskWalker"

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
        mavenLocal()
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
