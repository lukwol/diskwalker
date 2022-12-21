@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.compose.multiplatform)
}

dependencies {
    implementation(projects.domain)
    implementation(projects.libraries.formatters)
    implementation(projects.libraries.ranges)

    implementation(libs.viewmodel.screens.navigation)
    implementation(libs.windows.navigation)
    implementation(libs.async.state)
    implementation(libs.coroutines.core)
    implementation(libs.coroutines.swing)
    implementation(libs.koin.core)

    implementation(compose.desktop.currentOs)
    implementation(libs.compose.icons)
}

compose {
    kotlinCompilerPlugin.set(libs.versions.compose.compiler)
}
