@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.compose.multiplatform)
}

dependencies {
    implementation(projects.domain)
    implementation(projects.libraries.formatters)
    implementation(projects.libraries.ranges)

    implementation(libs.cmnav.screens.vm)
    implementation(libs.async.state)
    implementation(libs.coroutines.core)
    implementation(libs.coroutines.swing)
    implementation(libs.koin.core)
    implementation(libs.koin.compose)

    implementation(compose.desktop.currentOs)
    implementation(compose.material3)
    implementation(compose.materialIconsExtended)
}
