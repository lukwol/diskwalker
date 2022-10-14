import org.jetbrains.compose.compose

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id(libs.plugins.kotlin.jvm.get().pluginId)
    id(libs.plugins.compose.multiplatform.get().pluginId)
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":libraries:formatters"))
    implementation(project(":libraries:navigation"))
    implementation(project(":libraries:ranges"))
    implementation(project(":libraries:viewmodel"))

    implementation(libs.coroutines.core)
    implementation(libs.coroutines.swing)

    implementation(libs.koin.core)

    implementation(compose.desktop.currentOs)
    implementation(libs.compose.icons)
}
