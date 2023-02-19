package com.diskusage.domain.usecases.path

import java.nio.file.Path

/**
 * Traverse through all parents to find topmost [Path]
 */
class GetRoot {

    operator fun invoke(path: Path): Path {
        return path.parent?.let(::invoke) ?: path
    }
}
