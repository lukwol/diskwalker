import com.diskusage.libraries.utils.os.Arch
import com.diskusage.libraries.utils.os.OS
import com.diskusage.libraries.utils.os.OsUtils
import java.io.File

object SupportLibrary {
    fun loadLibrary() {
        val inputStream = javaClass.getResourceAsStream(filename)
        val file = File.createTempFile("disk-usage-", "-jni-lib")
        inputStream?.readBytes()?.let { file.writeBytes(it) }
        System.load(file.absolutePath)
        file.deleteOnExit()
    }

    private val filename: String
        get() = buildString {
            append("libsupport-")
            when (OsUtils.currentTarget) {
                OS.MacOS to Arch.X64 -> append("x86_64-apple-darwin.dylib")
                OS.MacOS to Arch.Arm64 -> append("aarch64-apple-darwin.dylib")
                OS.Linux to Arch.X64 -> append("x86_64-unknown-linux-gnu.so") // Doesn't exist
                OS.Linux to Arch.Arm64 -> append("aarch64-unknown-linux-gnu.so") // Doesn't exist
                OS.Windows to Arch.X64 -> append("x86_64-pc-windows-gnu.dll")
                OS.Windows to Arch.Arm64 -> append("aarch64-pc-windows-msvc.dll") // Doesn't exist
            }
        }
}
