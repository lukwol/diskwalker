@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id(libs.plugins.kotlin.jvm.get().pluginId)
}

dependencies {
    implementation(libs.coroutines.core)
    implementation(libs.coroutines.swing)

    testImplementation(libs.kotlin.test)
    testImplementation(libs.kotest.assertions)
    testImplementation(libs.coroutines.test)
}

tasks.test {
    useJUnitPlatform()
}
