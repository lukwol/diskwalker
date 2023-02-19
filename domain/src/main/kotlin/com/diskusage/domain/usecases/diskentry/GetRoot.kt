package com.diskusage.domain.usecases.diskentry

import java.nio.file.Path

/**
 * Traverse through all parents to find topmost [DiskEntry]
 */
class GetRoot {

    operator fun invoke(path: Path): Path {
        return path.parent?.let(::invoke) ?: path
    }
}
