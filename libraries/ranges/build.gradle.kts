plugins {
    kotlin(GradlePlugins.Kotlin.jvm)
}

dependencies {
    testImplementation(kotlin(Dependencies.Kotlin.test))
    testImplementation(Dependencies.Kotest.kotest)
}

tasks.test {
    useJUnitPlatform()
}
