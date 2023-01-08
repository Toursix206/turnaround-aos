package org.android.turnaround.domain.entity

data class Review(
    val content: String = "",
    val imageUrl: String = "",
    val name: String = "",
    val doneDate: String = "",
    val point: Int = 0,
    val score: Int = 0,
    val written: Boolean = false
)
