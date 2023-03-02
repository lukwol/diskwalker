import com.diskusage.libraries.utils.os.Arch
import com.diskusage.libraries.utils.os.OS
import com.diskusage.libraries.utils.os.OsUtils
import java.io.File

object SupportLibrary {
    fun loadLibrary() {
        val resourcesInputStream = javaClass.getResourceAsStream(filename)?.buffered()
        val file = File.createTempFile("disk-usage-", "-jni-lib")
        val fileOutputStream = file.outputStream().buffered()

        resourcesInputStream?.use { inputStream ->
            fileOutputStream.use { outputStream ->
                outputStream.write(inputStream.readBytes())
            }
        }
        System.load(file.absolutePath)
        file.deleteOnExit()
    }

    private val filename by lazy {
        when (OsUtils.Target) {
            OS.MacOS to Arch.X64 -> "libsupport-x86_64-apple-darwin.dylib"
            OS.MacOS to Arch.Arm64 -> "libsupport-aarch64-apple-darwin.dylib"
            OS.Windows to Arch.X64 -> "support-x86_64-pc-windows-gnu.dll"
            else -> error("Unsupported target ${OsUtils.Target}")
        }
    }
}
