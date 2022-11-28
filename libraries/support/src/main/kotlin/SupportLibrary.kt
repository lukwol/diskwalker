import java.io.File

object SupportLibrary {
    fun loadLibrary() {
        val filename = when (System.getProperty("os.arch")) {
            "aarch64" -> "libsupport-aarch64-apple-darwin.dylib"
            else -> "libsupport-x86_64-apple-darwin.dylib"
        }
        val inputStream = javaClass.getResourceAsStream(filename)
        val file = File.createTempFile("disk-usage-", "-jni-lib")
        inputStream?.readBytes()?.let { file.writeBytes(it) }
        System.load(file.absolutePath)
        file.deleteOnExit()
    }
}
