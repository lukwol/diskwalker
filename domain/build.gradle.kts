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
    implementation(project(":libraries:ranges"))

    implementation(Dependencies.Koin.core)

    implementation(compose.desktop.currentOs)

    testImplementation(Dependencies.Junit.junit5)
    testImplementation(Dependencies.Kotest.kotest)
    testImplementation(Dependencies.Mockk.mockk)
    testImplementation(Dependencies.Koin.test)
    testImplementation(Dependencies.Koin.junit5)
}

tasks.test {
    useJUnitPlatform()
}
