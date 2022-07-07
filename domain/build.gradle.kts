@file:Suppress("UnstableApiUsage")

import org.jetbrains.compose.compose

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id(libs.plugins.kotlin.jvm.get().pluginId)
    id(libs.plugins.compose.multiplatform.get().pluginId)
}

dependencies {
    implementation(project(":libraries:ranges"))

    implementation(libs.koin.core)

    implementation(compose.desktop.currentOs)

    testImplementation(libs.junit5)
    testImplementation(libs.kotest.assertions)
    testImplementation(libs.mockk)
    testImplementation(libs.koin.test)
    testImplementation(libs.koin.junit5)
}

tasks.test {
    useJUnitPlatform()
}
