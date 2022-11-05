@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.dokka)
}

dependencies {
    api(libs.viewmodel)
    api(project(":libraries:screens-navigation"))

    implementation(compose.desktop.currentOs)

    testImplementation(libs.junit4)
    testImplementation(libs.kotlin.test)
    testImplementation(libs.kotest.assertions)
    testImplementation(libs.coroutines.test)
    testImplementation(libs.compose.test)
    testImplementation(libs.compose.test.junit4)
}

compose {
    kotlinCompilerPlugin.set(libs.versions.compose.compiler)
}
