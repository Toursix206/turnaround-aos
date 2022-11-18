package org.android.turnaround.domain.entity

data class TodoDetail(
    val activityId: Int,
    val broom: Int,
    val category: String,
    val name: String,
    val point: Int,
    val rewardItem: String? = null,
    val type: String,
    val categoryName: String,
    val categoryImage: Int
)
