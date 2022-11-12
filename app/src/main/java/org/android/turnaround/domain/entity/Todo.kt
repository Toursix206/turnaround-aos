package org.android.turnaround.domain.entity

sealed class Todo

data class TodoBlack(
    val category: String,
    val title: String,
    val image: Int
) : Todo()

data class TodoWhite(
    val category: String,
    val title: String,
    val timeInfo: String,
    val image: Int
) : Todo()

data class TodoPurple(
    val category: String,
    val title: String,
    val image: Int
) : Todo()

data class NoTodo(
    val nickname: String
) : Todo()
