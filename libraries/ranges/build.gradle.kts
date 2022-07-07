@Suppress(
    "DSL_SCOPE_VIOLATION",
    "UnstableApiUsage",
)
plugins {
    id(libs.plugins.kotlin.jvm.get().pluginId)
}

dependencies {
    testImplementation(kotlin(Dependencies.Kotlin.test))
    testImplementation(Dependencies.Kotest.kotest)
}

tasks.test {
    useJUnitPlatform()
}
