package org.android.turnaround.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TodoDetail(
    val todoId: Int,
    val activityId: Int,
    val broom: Int,
    val category: String,
    val name: String,
    val point: Int,
    val pushStatus: TodoPushType = TodoPushType.ON,
    val rewardItem: String? = null,
    val type: String,
    val categoryName: String,
    val categoryImage: Int,
    val leftTime: String,
    val isAfterStartAt: Boolean
) : Parcelable
