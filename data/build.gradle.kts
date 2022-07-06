plugins {
    kotlin(GradlePlugins.Kotlin.jvm)
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":libraries:support"))

    implementation(Dependencies.Koin.core)

    testImplementation(Dependencies.Junit.junit5)
    testImplementation(Dependencies.Kotest.kotest)
    testImplementation(Dependencies.Mockk.mockk)
    testImplementation(Dependencies.Koin.test)
    testImplementation(Dependencies.Koin.junit5)
}

tasks.test {
    useJUnitPlatform()
}
