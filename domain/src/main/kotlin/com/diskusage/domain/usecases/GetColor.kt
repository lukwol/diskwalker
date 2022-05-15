package com.diskusage.domain.usecases

import androidx.compose.ui.graphics.Color
import com.diskusage.domain.entities.DiskEntry

class GetColor {
    operator fun invoke(diskEntry: DiskEntry): Color {
        // TODO: 15/05/2022 Implement
        return Color.Black
    }
}
