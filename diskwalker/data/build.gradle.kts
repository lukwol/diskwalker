import com.google.protobuf.gradle.id

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.protobuf)
}

dependencies {
    implementation(projects.domain)
    implementation(projects.libraries.support)

    implementation(libs.protobuf.java)
    implementation(libs.protobuf.kotlin)
    implementation(libs.koin.core)
    implementation(libs.async.core)
    implementation(libs.coroutines.core)

    testImplementation(libs.junit5)
    testImplementation(libs.kotest.assertions)
    testImplementation(libs.mockk)
    testImplementation(libs.koin.test)
    testImplementation(libs.koin.junit5)
    testImplementation(libs.coroutines.test)
}

sourceSets {
    main {
        proto {
            srcDirs("${rootDir.parent}/protos")
        }
    }
}

protobuf {
    generateProtoTasks {
        all().forEach {
            it.builtins {
                id("kotlin")
            }
        }
    }
}

tasks.formatKotlinMain {
    exclude { it.file.path.contains("generated/") }
}

tasks.lintKotlinMain {
    exclude { it.file.path.contains("generated/") }
}

tasks.test {
    useJUnitPlatform()
}
