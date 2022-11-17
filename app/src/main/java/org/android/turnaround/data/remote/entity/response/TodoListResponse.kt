package org.android.turnaround.data.remote.entity.response

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
}
