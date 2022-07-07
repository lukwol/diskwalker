package com.diskusage.presentation.common.blocks

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.AwtWindow
import androidx.compose.ui.window.FrameWindowScope
import com.diskusage.presentation.common.currentOS
import org.jetbrains.skiko.OS
import java.awt.FileDialog
import java.io.File
import java.nio.file.Path

enum class FileDialogMode(val value: Int) {
    Load(FileDialog.LOAD),
    Save(FileDialog.SAVE)
}

@Preview
@Composable
fun FrameWindowScope.FileDialog(
    title: String,
    mode: FileDialogMode,
    onResult: (result: Path?) -> Unit
) = AwtWindow(
    create = {
        if (currentOS == OS.MacOS) {
            System.setProperty("apple.awt.fileDialogForDirectories", "true")
        }
        object : FileDialog(window, title, mode.value) {
            override fun setVisible(value: Boolean) {
                super.setVisible(value)
                if (value) {
                    if (file != null) {
                        onResult(File(directory).resolve(file).toPath())
                    } else {
                        onResult(null)
                    }
                }
            }
        }
    },
    dispose = FileDialog::dispose
)
