import org.jetbrains.compose.compose

plugins {
    kotlin(GradlePlugins.Kotlin.jvm)
    id(GradlePlugins.Compose.id)
}

dependencies {
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
