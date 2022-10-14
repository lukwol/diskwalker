import org.jetbrains.compose.compose

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id(libs.plugins.kotlin.jvm.get().pluginId)
    id(libs.plugins.compose.multiplatform.get().pluginId)
}

dependencies {
    implementation(compose.desktop.currentOs)

    testImplementation(libs.kotlin.test)
    testImplementation(libs.kotest.assertions)
}

tasks.test {
    useJUnitPlatform()
}
