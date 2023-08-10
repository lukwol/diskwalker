package com.diskwalker.domain.model.errors

import java.nio.file.Path

class MissingChildren(path: Path) : Throwable("No children were found for path: $path")
