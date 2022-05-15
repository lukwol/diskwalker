@file:Suppress("unused", "SpellCheckingInspection", "MemberVisibilityCanBePrivate")

object Dependencies {
    object Coroutines {
        private const val version = "1.6.0"
        const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$version"
        const val test = "org.jetbgrains.kotlinx:kotlinx-coroutines-test:$version"
    }

    object Koin {
        private const val version = "3.1.5"
        const val core = "io.insert-koin:koin-core:$version"
        const val test = "io.insert-koin:koin-test:$version"
        const val junit5 = "io.insert-koin:koin-test-junit5:$version"
    }

    object Junit {
        private const val version = "5.8.2"
        const val junit5 = "org.junit.jupiter:junit-jupiter-api:$version"
    }

    object Mockk {
        private const val version = "1.12.2"
        const val mockk = "io.mockk:mockk:$version"
    }

    object Kotest {
        private const val version = "5.1.0"
        const val kotest = "io.kotest:kotest-assertions-core-jvm:$version"
    }
}
