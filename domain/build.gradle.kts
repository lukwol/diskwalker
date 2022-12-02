@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.compose.multiplatform)
}

dependencies {
    implementation(project(":libraries:ranges"))
    implementation(project(":libraries:utils"))

    implementation(libs.koin.core)
    implementation(libs.coroutines.core)
    implementation(libs.coroutines.swing)

    implementation(compose.desktop.currentOs)

    testImplementation(libs.junit5)
    testImplementation(libs.kotest.assertions)
    testImplementation(libs.mockk)
    testImplementation(libs.koin.test)
    testImplementation(libs.koin.junit5)
    testImplementation(libs.coroutines.test)
}

tasks.test {
    useJUnitPlatform()
}

compose {
    kotlinCompilerPlugin.set(libs.versions.compose.compiler)
}
