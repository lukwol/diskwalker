import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jetbrains.kotlin.util.capitalizeDecapitalize.toLowerCaseAsciiOnly

@Suppress(
    "DSL_SCOPE_VIOLATION",
    "UnstableApiUsage",
)
plugins {
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.compose.multiplatform) apply false
    alias(libs.plugins.dependency.updates)
    alias(libs.plugins.spotless)
}

allprojects {
    repositories {
        mavenCentral()
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "16"
    }
}

subprojects {
    tasks.withType<KotlinCompile>().all {
        kotlinOptions.freeCompilerArgs += "-opt-in=kotlin.RequiresOptIn"
    }
}

spotless {
    kotlin {
        target("**/*.kt")
        targetExclude("$buildDir/**/*.kt")
        targetExclude("bin/**/*.kt")

        ktlint(libs.versions.ktlint.get())
            .userData(mapOf("disabled_rules" to "no-wildcard-imports"))
    }
}

tasks.withType<DependencyUpdatesTask> {
    rejectVersionIf {
        candidate.version.toLowerCaseAsciiOnly().run {
            listOf("-alpha", "-beta", "-rc").any { contains(it) }
        }
    }
}
