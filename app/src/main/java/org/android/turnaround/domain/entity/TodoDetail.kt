package org.android.turnaround.domain.entity

data class TodoDetail(
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
)
