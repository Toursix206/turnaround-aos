package org.android.turnaround.util.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import org.android.turnaround.R
import org.android.turnaround.domain.entity.CleanLevel
import org.android.turnaround.domain.entity.FurnitureType

object BindingAdapters {
    @JvmStatic
    @BindingAdapter(value = ["imgUrl", "isCrop"], requireAll = false)
    fun ImageView.initImage(imgUrl: String?, isCrop: Boolean?) {
        isCrop?.let {
            clipToOutline = it
        }
        imgUrl?.let {
            Glide.with(context)
                .load(it)
                .placeholder(R.color.turnaround_gray_7)
                .error(R.color.turnaround_gray_7)
                .into(this)
        }
    }

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

    @JvmStatic
    @BindingAdapter("roomScore")
    fun ImageView.initRoomScoreEmoji(roomScore: Int) {
        val goodScore = 61..100
        val soSoSCore = 31..60
        val badScore = 0..30
        when (roomScore) {
            in badScore -> setImageResource(R.drawable.ic_room_room_score_bad)
            in soSoSCore -> setImageResource(R.drawable.ic_room_room_score_so_so)
            in goodScore -> setImageResource(R.drawable.ic_room_room_score_good)
        }
    }
}