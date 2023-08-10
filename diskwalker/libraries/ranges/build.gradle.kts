@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.kotlin.jvm)
}

dependencies {
    testImplementation(libs.junit5)
    testImplementation(libs.kotlin.test)
    testImplementation(libs.kotest.assertions)
}

tasks.test {
    useJUnitPlatform()
}
