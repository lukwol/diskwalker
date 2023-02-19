package com.diskusage.domain.model.errors

import java.nio.file.Path

class MissingPathInfo(path: Path) : Throwable("No path info was found for path: $path")
