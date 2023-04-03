package com.diskwalker.libraries.support

class SupportJni {

    external fun sizeOnDisk(path: String): Long

    external fun serializedSystemInfo(): ByteArray
}
