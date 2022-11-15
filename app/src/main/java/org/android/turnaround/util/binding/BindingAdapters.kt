package org.android.turnaround.util.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import org.android.turnaround.R
import org.android.turnaround.domain.entity.CleanScore
import org.android.turnaround.domain.entity.FurnitureType

object BindingAdapters {
    @JvmStatic
    @BindingAdapter(value = ["cleanScore", "furnitureType"], requireAll = true)
    fun ImageView.initFurniture(cleanScore: CleanScore, furnitureType: FurnitureType) {
        setImageResource(
            when (furnitureType) {
                FurnitureType.WINDOW -> {
                    when (cleanScore) {
                        CleanScore.MOST_CLEAN -> R.drawable.ic_roomtaverse_window_1
                        CleanScore.SO_SO -> R.drawable.ic_roomtaverse_window_2
                        CleanScore.MOST_DIRTY -> R.drawable.ic_roomtaverse_window_3
                    }
                }
                FurnitureType.BED -> {
                    when (cleanScore) {
                        CleanScore.MOST_CLEAN -> R.drawable.ic_roomtaverse_bed_1
                        CleanScore.SO_SO -> R.drawable.ic_roomtaverse_bed_2
                        CleanScore.MOST_DIRTY -> R.drawable.ic_roomtaverse_bed_3
                    }
                }
                FurnitureType.DESK -> {
                    when (cleanScore) {
                        CleanScore.MOST_CLEAN -> R.drawable.ic_roomtaverse_desk_1
                        CleanScore.SO_SO -> R.drawable.ic_roomtaverse_desk_2
                        CleanScore.MOST_DIRTY -> R.drawable.ic_roomtaverse_desk_3
                    }
                }
            }
        )
    }
}
