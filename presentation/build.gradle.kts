import org.jetbrains.compose.compose

@Suppress(
    "DSL_SCOPE_VIOLATION",
    "UnstableApiUsage",
)
plugins {
    id(libs.plugins.kotlin.jvm.get().pluginId)
    id(libs.plugins.compose.multiplatform.get().pluginId)
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":libraries:ranges"))

    implementation(Dependencies.Coroutines.core)
    implementation(Dependencies.Coroutines.swing)

    implementation(Dependencies.Koin.core)

    implementation(compose.desktop.currentOs)
}
