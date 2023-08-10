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
    implementation(projects.libraries.utils)

    implementation(libs.koin.core)
    implementation(libs.coroutines.core)
    implementation(libs.coroutines.swing)

    implementation(compose.desktop.currentOs)
    implementation(compose.material3)
}

compose {
    desktop {
        application {
            mainClass = "com.diskwalker.app.MainKt"

            val resourcesPath = "src/main/resources"

            nativeDistributions {
                targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
                appResourcesRootDir.set(project.layout.projectDirectory.dir(resourcesPath))

                packageName = "DiskWalker"
                packageVersion = "1.0.0"

                macOS {
                    jvmArgs("-Dapple.awt.application.appearance=system")
                    iconFile.set(project.file("$resourcesPath/diskwalker_icon.icns"))
                }

                windows {
                    iconFile.set(project.file("$resourcesPath/diskwalker_icon.ico"))
                }

                linux {
                    iconFile.set(project.file("$resourcesPath/diskwalker_icon.png"))
                }
            }

            buildTypes.release.proguard {
                configurationFiles.from(project.file("$resourcesPath/diskwalker.pro"))
                obfuscate.set(true)
            }
        }
    }
}
