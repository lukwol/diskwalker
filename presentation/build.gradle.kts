import org.jetbrains.compose.compose

plugins {
    kotlin(GradlePlugins.Kotlin.jvm)
    id(GradlePlugins.Compose.id)
    id(GradlePlugins.Ksp.id)
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":libraries:ranges"))

    implementation(Dependencies.Koin.core)
    implementation(Dependencies.Koin.Annotations.annotations)
    ksp(Dependencies.Koin.Annotations.compiler)

    implementation(compose.desktop.currentOs)
}

sourceSets.main {
    java.srcDirs(BuildConstants.kspGeneratedSourceCodePath)
}
