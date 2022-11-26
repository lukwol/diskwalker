package com.diskusage.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.AwtWindow
import androidx.compose.ui.window.FrameWindowScope
import java.awt.FileDialog
import java.io.File
import java.nio.file.Path

enum class FileDialogMode(val value: Int) {
    Load(FileDialog.LOAD),
    Save(FileDialog.SAVE)
}

@Composable
fun FrameWindowScope.FileDialog(
    title: String,
    mode: FileDialogMode,
    onResult: (result: Path?) -> Unit
) = AwtWindow(
    create = {
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
