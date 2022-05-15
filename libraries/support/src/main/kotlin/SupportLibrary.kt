import java.io.File

object SupportLibrary {
    fun loadLibrary() {
        val inputStream = javaClass.getResourceAsStream("libsupport.dylib")
        val file = File.createTempFile("disk-usage-", "-jni-lib")
        inputStream?.readBytes()?.let { file.writeBytes(it) }
        System.load(file.absolutePath)
        file.deleteOnExit()
    }
}
