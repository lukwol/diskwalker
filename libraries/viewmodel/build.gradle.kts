@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.dokka)
}

dependencies {
    implementation(libs.coroutines.core)
    implementation(libs.coroutines.swing)

    testImplementation(libs.junit5)
    testImplementation(libs.kotlin.test)
    testImplementation(libs.kotest.assertions)
    testImplementation(libs.coroutines.test)
}

tasks.test {
    useJUnitPlatform()
}
