@file:Suppress("UnstableApiUsage")

import org.jetbrains.compose.compose
import org.jetbrains.compose.desktop.application.dsl.TargetFormat

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id(libs.plugins.kotlin.jvm.get().pluginId)
    id(libs.plugins.compose.multiplatform.get().pluginId)
}

dependencies {
    implementation(project(":data"))
    implementation(project(":domain"))
    implementation(project(":presentation"))
    implementation(project(":libraries:support"))

    implementation(libs.koin.core)

    implementation(compose.desktop.currentOs)
}

compose.desktop {
    application {
        mainClass = "com.diskusage.app.MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "DiskUsage"
            packageVersion = "1.0.0"
        }
    }
}
