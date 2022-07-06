plugins {
    kotlin(GradlePlugins.Kotlin.jvm)
    id(GradlePlugins.Ksp.id)
}

dependencies {
    implementation(project(":domain"))

    implementation(Dependencies.Koin.core)
    implementation(Dependencies.Koin.Annotations.annotations)
    ksp(Dependencies.Koin.Annotations.compiler)

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
