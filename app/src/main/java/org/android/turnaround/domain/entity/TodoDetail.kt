package org.android.turnaround.domain.entity

data class TodoDetail(
    val activityId: Int,
    val broom: Int,
    val category: String,
    val name: String,
    val point: Int,
    val rewardItem: String,
    val type: String
)
