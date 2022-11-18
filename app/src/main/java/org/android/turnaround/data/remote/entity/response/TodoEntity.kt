package org.android.turnaround.data.remote.entity.response

data class TodoEntity(
    val activityCategory: String,
    val activityName: String,
    val leftTime: String,
    val todoId: Int,
    val todoStatus: String
)
