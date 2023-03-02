import com.google.protobuf.gradle.id

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.protobuf)
}

dependencies {
    implementation(projects.domain)
    implementation(projects.libraries.support)

    implementation(libs.protobuf.javalite)
    implementation(libs.protobuf.kotlin.lite)
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
            srcDirs("${rootProject.projectDir.path}/protos")
        }
    }
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:${libs.versions.protobuf.get()}"
    }

    generateProtoTasks {
        all().forEach {
            it.builtins {
                getByName("java") {
                    option("lite")
                }
                id("kotlin") {
                    option("lite")
                }
            }
        }
    }
}

tasks.test {
    useJUnitPlatform()
}
