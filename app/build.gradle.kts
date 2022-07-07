import org.jetbrains.compose.compose
import org.jetbrains.compose.desktop.application.dsl.TargetFormat

@Suppress(
    "DSL_SCOPE_VIOLATION",
    "UnstableApiUsage",
)
plugins {
    id(libs.plugins.kotlin.jvm.get().pluginId)
    id(libs.plugins.compose.multiplatform.get().pluginId)
}

dependencies {
    implementation(project(":data"))
    implementation(project(":domain"))
    implementation(project(":presentation"))
    implementation(project(":libraries:support"))

    implementation(Dependencies.Koin.core)

    implementation(compose.desktop.currentOs)
}

compose.desktop {
    application {
        mainClass = BuildConstants.mainClass
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = BuildConstants.packageName
            packageVersion = BuildConstants.packageVersion
        }
    }
}
