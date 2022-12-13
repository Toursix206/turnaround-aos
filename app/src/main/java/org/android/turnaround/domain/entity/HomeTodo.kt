package org.android.turnaround.domain.entity

data class HomeTodo(
    val activityCategory: String,
    val activityName: String,
    val leftTime: String,
    val todoId: Int,
    val todoStatus: TodoType,
    val duration: Int,
    val categoryName: String,
    val categoryImage: Int
)
