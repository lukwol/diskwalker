import org.jetbrains.compose.desktop.application.dsl.TargetFormat

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.compose.multiplatform)
}

dependencies {
    implementation(project(":data"))
    implementation(project(":domain"))
    implementation(project(":presentation"))
    implementation(project(":libraries:support"))

    implementation(libs.koin.core)

    implementation(compose.desktop.currentOs)
}

compose {
    kotlinCompilerPlugin.set(libs.versions.compose.compiler)

    desktop {
        application {
            mainClass = "com.diskusage.app.MainKt"
            nativeDistributions {
                targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
                packageName = "DiskUsage"
                packageVersion = "1.0.0"
            }
        }
    }
}
