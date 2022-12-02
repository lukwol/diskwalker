package com.diskusage.domain.services

interface DisksService {
    fun name(path: String): String?
    fun totalSpace(path: String): Long
    fun availableSpace(path: String): Long
}
