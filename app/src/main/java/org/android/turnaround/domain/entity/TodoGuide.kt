package org.android.turnaround.domain.entity

data class TodoGuide(
    val activityId: Int,
    val name: String,
    val guides: List<Guide>
)

data class Guide(
    val step: Int,
    val title: String,
    val content: String?,
    val imageUrl: String
)
