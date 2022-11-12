package org.android.turnaround.data.remote.entity.response

import org.android.turnaround.R
import org.android.turnaround.domain.entity.ActivityType
import org.android.turnaround.domain.entity.Todo
import org.android.turnaround.domain.entity.TodoList

data class TodoListResponse(
    val nextTodos: List<TodoEntity>,
    val nextTodosCnt: Int,
    val successTodos: List<TodoEntity>,
    val successTodosCnt: Int,
    val thisWeekTodos: List<TodoEntity>,
    val thisWeekTodosCnt: Int,
    val todayTodos: List<TodoEntity>,
    val todayTodosCnt: Int
) {
    fun toTodoList(): TodoList =
        TodoList(
            nextTodos = toTodos(this.nextTodos),
            nextTodosCnt = this.nextTodosCnt,
            successTodos = toTodos(this.successTodos),
            successTodosCnt = this.successTodosCnt,
            thisWeekTodos = toTodos(this.thisWeekTodos),
            thisWeekTodosCnt = this.thisWeekTodosCnt,
            todayTodos = toTodos(this.todayTodos),
            todayTodosCnt = this.todayTodosCnt
        )

    private fun toTodos(todos: List<TodoEntity>): List<Todo> {
        val list = mutableListOf<Todo>()
        todos.forEach {
            list.add(
                Todo(
                    activityCategory = it.activityCategory,
                    activityName = it.activityName,
                    leftTime = it.leftTime,
                    todoId = it.todoId,
                    todoStatus = it.todoStatus,
                    categoryName = toCategoryName(it.activityCategory),
                    categoryImage = toCategoryImage(it.activityCategory)
                )
            )
        }
        return list
    }

    private fun toCategoryImage(activityCategory: String): Int = when (activityCategory) {
        ActivityType.BEDDING.type -> R.drawable.img_todo_bedding
        ActivityType.TABLE.type -> R.drawable.img_todo_table
        ActivityType.KITCHEN.type -> R.drawable.img_todo_kitchen
        ActivityType.RESTROOM.type -> R.drawable.img_todo_restroom
        ActivityType.SELF_DEVELOPMENT.type -> R.drawable.img_todo_self_development
        ActivityType.ETC.type -> R.drawable.img_todo_etc
        ActivityType.WASHER.type -> R.drawable.img_todo_washer
        else -> R.drawable.img_todo_self_development
    }

    private fun toCategoryName(activityCategory: String): String = when (activityCategory) {
        ActivityType.BEDDING.type -> "침대"
        ActivityType.TABLE.type -> "책상"
        ActivityType.KITCHEN.type -> "주방"
        ActivityType.RESTROOM.type -> "화장실"
        ActivityType.SELF_DEVELOPMENT.type -> "자기계발"
        ActivityType.ETC.type -> "기타가구"
        ActivityType.WASHER.type -> "세탁기"
        else -> "기타가구"
    }
}
