package org.android.turnaround.domain.entity

data class Activity(
    val lastPage: Int,
    val nextPage: Int,
    val contents: List<Content>
)

data class Content(
    val activityId: Int,
    val broom: Int,
    val category: String,
    val type: String,
    val name: String,
    val description: String,
    val duration: Int,
    val imageUrl: String
)
