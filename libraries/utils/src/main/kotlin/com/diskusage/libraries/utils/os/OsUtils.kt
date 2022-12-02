package com.diskusage.libraries.utils.os

object OsUtils {
    val currentTarget by lazy {
        currentOS to currentArch
    }

    val currentArch by lazy {
        when (val osArch = System.getProperty("os.arch")) {
            "x86_64", "amd64" -> Arch.X64
            "aarch64" -> Arch.Arm64
            else -> error("Unsupported OS arch: $osArch")
        }
    }

    val currentOS by lazy {
        val os = System.getProperty("os.name")
        when {
            os.equals("Mac OS X", ignoreCase = true) -> OS.MacOS
            os.startsWith("Win", ignoreCase = true) -> OS.Windows
            os.startsWith("Linux", ignoreCase = true) -> OS.Linux
            else -> error("Unknown OS name: $os")
        }
    }
}
