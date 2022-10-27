import org.jetbrains.compose.compose

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.dokka)
}

dependencies {
    api(project(":libraries:viewmodel"))
    api(project(":libraries:screens-navigation"))

    implementation(compose.desktop.currentOs)

    testImplementation(libs.junit4)
    testImplementation(libs.kotlin.test)
    testImplementation(libs.kotest.assertions)
    testImplementation(libs.coroutines.test)
    testImplementation(libs.compose.test)
    testImplementation(libs.compose.test.junit4)
}
