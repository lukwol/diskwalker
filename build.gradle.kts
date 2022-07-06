import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jetbrains.kotlin.util.capitalizeDecapitalize.toLowerCaseAsciiOnly

plugins {
    kotlin(GradlePlugins.Kotlin.jvm) version Common.Kotlin.version apply false
    id(GradlePlugins.Compose.id) version GradlePlugins.Compose.version apply false
    id(GradlePlugins.Ksp.id) version GradlePlugins.Ksp.version apply false
    id(GradlePlugins.Versions.id) version GradlePlugins.Versions.version
    id(GradlePlugins.Spotless.id) version GradlePlugins.Spotless.version
}

allprojects {
    repositories {
        mavenCentral()
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions.jvmTarget = BuildConstants.jvmTarget
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

        ktlint(GradlePlugins.Spotless.ktlintVersion)
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
