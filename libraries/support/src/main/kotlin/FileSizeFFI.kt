import com.diskusage.support.FileSize

object FileSizeFFI : FileSize {
    external override fun sizeOnDisk(path: String): Long
    external override fun sizeOnDiskFast(path: String): Long
}
