import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jetbrains.kotlin.util.capitalizeDecapitalize.toLowerCaseAsciiOnly

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.compose.multiplatform) apply false
    alias(libs.plugins.dependency.updates)
    alias(libs.plugins.kotlinter)
    alias(libs.plugins.dokka)
}

allprojects {
    apply {
        plugin("org.jmailen.kotlinter")
        plugin("org.jetbrains.dokka")
    }

    repositories {
        mavenCentral()
        maven("https://jitpack.io")
    }

    group = "io.github.lukwol"
    version = "1.0.0"

    tasks.withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "16"
        kotlinOptions.freeCompilerArgs += "-opt-in=kotlin.RequiresOptIn"
    }

    kotlinter {
        disabledRules = arrayOf("no-wildcard-imports")
    }
}

tasks.withType<DependencyUpdatesTask> {
    rejectVersionIf {
        candidate.version.toLowerCaseAsciiOnly().run {
            listOf("-alpha", "-beta", "-rc").any(::contains)
        }
    }
}
