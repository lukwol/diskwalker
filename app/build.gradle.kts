import org.jetbrains.compose.desktop.application.dsl.TargetFormat

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.compose.multiplatform)
}

dependencies {
    implementation(projects.data)
    implementation(projects.domain)
    implementation(projects.presentation)
    implementation(projects.libraries.support)

    implementation(libs.koin.core)
    implementation(libs.coroutines.core)
    implementation(libs.coroutines.swing)

    implementation(compose.desktop.currentOs)
}

compose {
    desktop {
        application {
            buildTypes.release.proguard {
                configurationFiles.from(project.file("custom.pro"))
            }

            mainClass = "com.diskwalker.app.MainKt"
            nativeDistributions {
                targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
                packageName = "DiskWalker"
                packageVersion = "1.0.0"

                macOS {
                    jvmArgs("-Dapple.awt.application.appearance=system")
                    iconFile.set(project.file("diskwalker-icon.icns"))
                }

                windows {
                    iconFile.set(project.file("diskwalker-icon.ico"))
                }

                linux {
                    iconFile.set(project.file("diskwalker-icon.png"))
                }
            }
        }
    }
}
