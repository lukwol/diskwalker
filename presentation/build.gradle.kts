import org.jetbrains.compose.compose

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.compose.multiplatform)
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":libraries:formatters"))
    implementation(project(":libraries:ranges"))
    implementation(project(":libraries:windows-navigation"))
    implementation(project(":libraries:viewmodel-screens-navigation"))

    implementation(libs.coroutines.core)
    implementation(libs.coroutines.swing)

    implementation(libs.koin.core)

    implementation(compose.desktop.currentOs)
    implementation(libs.compose.icons)
}
