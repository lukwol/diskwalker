import com.diskusage.domain.services.FileSizeService

object FileSizeFFI : FileSizeService {
    external override fun sizeOnDisk(path: String): Long
    external override fun sizeOnDiskFast(path: String): Long
}
