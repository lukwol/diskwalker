import org.jetbrains.compose.compose

plugins {
    kotlin(GradlePlugins.Kotlin.jvm)
    id(GradlePlugins.Compose.id)
    id(GradlePlugins.Ksp.id)
}

dependencies {
    implementation(project(":libraries:ranges"))

    implementation(Dependencies.Koin.core)
    implementation(Dependencies.Koin.Annotations.annotations)
    ksp(Dependencies.Koin.Annotations.compiler)

    implementation(compose.desktop.currentOs)

    testImplementation(Dependencies.Junit.junit5)
    testImplementation(Dependencies.Kotest.kotest)
    testImplementation(Dependencies.Mockk.mockk)
    testImplementation(Dependencies.Koin.test)
    testImplementation(Dependencies.Koin.junit5)
}

sourceSets.main {
    java.srcDirs(BuildConstants.kspGeneratedSourceCodePath)
}

tasks.test {
    useJUnitPlatform()
}
