package com.diskusage.presentation.common

import org.jetbrains.skiko.OS

// TODO: Do not use skiko, move to separate library and use it in SupportLibrary

val currentOS: OS by lazy {
    val os = System.getProperty("os.name")
    when {
        os.equals("Mac OS X", ignoreCase = true) -> OS.MacOS
        os.startsWith("Win", ignoreCase = true) -> OS.Windows
        os.startsWith("Linux", ignoreCase = true) -> OS.Linux
        else -> error("Unknown OS name: $os")
    }
}
