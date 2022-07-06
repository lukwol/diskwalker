plugins {
    kotlin(GradlePlugins.Kotlin.jvm)
}

dependencies {
    testImplementation(Dependencies.Junit.junit5)
    testImplementation(Dependencies.Kotest.kotest)
}

tasks.test {
    useJUnitPlatform()
}
