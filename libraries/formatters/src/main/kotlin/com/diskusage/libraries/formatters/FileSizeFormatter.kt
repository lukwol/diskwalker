package com.diskusage.libraries.formatters

import java.text.DecimalFormat

private const val B = 1L
private const val KiB = B shl 10
private const val MiB = KiB shl 10
private const val GiB = MiB shl 10
private const val TiB = GiB shl 10
private const val PiB = TiB shl 10
private const val EiB = PiB shl 10

private const val KB = B * 1000
private const val MB = KB * 1000
private const val GB = MB * 1000
private const val TB = GB * 1000
private const val PB = TB * 1000
private const val EB = PB * 1000

private val DecimalFormat = DecimalFormat("#.##")

/**
 * Converts byte size into human-readable format.
 */
object FileSizeFormatter {

    /**
     * Format byte [size] using binary prefixes
     */
    fun toBinaryFormat(size: Long) = when {
        size < 0 -> throw IllegalArgumentException("Invalid file size: $size")
        size >= EiB -> formatSize(size, EiB, "EiB")
        size >= PiB -> formatSize(size, PiB, "PiB")
        size >= TiB -> formatSize(size, TiB, "TiB")
        size >= GiB -> formatSize(size, GiB, "GiB")
        size >= MiB -> formatSize(size, MiB, "MiB")
        size >= KiB -> formatSize(size, KiB, "KiB")
        else -> formatSize(size, B, "Bytes")
    }

    /**
     * Format byte [size] using SI prefixes
     */
    fun toSiFormat(size: Long) = when {
        size < 0 -> throw IllegalArgumentException("Invalid file size: $size")
        size >= EB -> formatSize(size, EB, "EB")
        size >= PB -> formatSize(size, PB, "PB")
        size >= TB -> formatSize(size, TB, "TB")
        size >= GB -> formatSize(size, GB, "GB")
        size >= MB -> formatSize(size, MB, "MB")
        size >= KB -> formatSize(size, KB, "KB")
        else -> formatSize(size, B, "Bytes")
    }

    /**
     * Divides [size] by [divider] and appends [unitName] at the end
     *
     * @param size byte size to format
     * @param divider value for unit prefix by which result should be divided
     * @param unitName name of the unit appended at the end
     */
    private fun formatSize(size: Long, divider: Long, unitName: String) =
        DecimalFormat.format(size.toDouble() / divider) + " " + unitName
}
