package org.android.turnaround.domain.entity

data class TodoList(
    val nextTodos: List<Todo>,
    val nextTodosCnt: Int,
    val successTodos: List<Todo>,
    val successTodosCnt: Int,
    val thisWeekTodos: List<Todo>,
    val thisWeekTodosCnt: Int,
    val todayTodos: List<Todo>,
    val todayTodosCnt: Int
)

sealed class TodoEvent

data class Todo(
    val activityCategory: String,
    val activityName: String,
    val leftTime: String,
    val todoId: Int,
    val todoStatus: String,
    val duration: Int,
    val categoryName: String,
    val categoryImage: Int
) : TodoEvent()

data class TodoHeader(
    val title: String,
    val count: Int
) : TodoEvent()
