package com.diskwalker.domain.model.errors

import java.nio.file.Path

class ScanCancelled(path: Path) : Throwable("Scan was cancelled for path: $path")
