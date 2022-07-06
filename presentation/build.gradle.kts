import org.jetbrains.compose.compose

plugins {
    kotlin(GradlePlugins.Kotlin.jvm)
    id(GradlePlugins.Compose.id)
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":libraries:core"))

    implementation(Dependencies.Koin.core)
    implementation(compose.desktop.currentOs)
}
