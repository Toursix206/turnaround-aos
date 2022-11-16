package org.android.turnaround.util.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import org.android.turnaround.R
import org.android.turnaround.domain.entity.CleanLevel
import org.android.turnaround.domain.entity.FurnitureType

object BindingAdapters {
    @JvmStatic
    @BindingAdapter(value = ["cleanLevel", "furnitureType"], requireAll = true)
    fun ImageView.initFurniture(cleanLevel: CleanLevel, furnitureType: FurnitureType) {
        when (furnitureType) {
            FurnitureType.BASIC_WALL -> {}
            FurnitureType.BASIC_WINDOW -> {
                setImageResource(
                    when (cleanLevel) {
                        CleanLevel.CLEAN -> R.drawable.ic_roomtaverse_window_1
                        CleanLevel.DIRTY -> R.drawable.ic_roomtaverse_window_2
                        CleanLevel.VERY_DIRTY -> R.drawable.ic_roomtaverse_window_3
                    }
                )
            }
            FurnitureType.BASIC_BED -> {
                setImageResource(
                    when (cleanLevel) {
                        CleanLevel.CLEAN -> R.drawable.ic_roomtaverse_bed_1
                        CleanLevel.DIRTY -> R.drawable.ic_roomtaverse_bed_2
                        CleanLevel.VERY_DIRTY -> R.drawable.ic_roomtaverse_bed_3
                    }
                )
            }
            FurnitureType.BASIC_TABLE -> {
                setImageResource(
                    when (cleanLevel) {
                        CleanLevel.CLEAN -> R.drawable.ic_roomtaverse_table_1
                        CleanLevel.DIRTY -> R.drawable.ic_roomtaverse_table_2
                        CleanLevel.VERY_DIRTY -> R.drawable.ic_roomtaverse_table_3
                    }
                )
            }
        }
    }
}
