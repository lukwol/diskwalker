@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.kotlin.jvm)
}

dependencies {
    implementation(projects.domain)

    implementation(libs.koin.core)
    implementation(libs.async.core)
    implementation(libs.coroutines.core)

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
