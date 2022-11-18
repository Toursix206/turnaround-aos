package org.android.turnaround.data.remote.entity.response

import org.android.turnaround.domain.entity.Todo
import org.android.turnaround.domain.entity.TodoCategory
import org.android.turnaround.domain.entity.TodoImageCategory
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
            nextTodos = this.nextTodos.map { todo ->
                Todo(
                    activityCategory = todo.activityCategory,
                    activityName = todo.activityName,
                    leftTime = todo.leftTime,
                    todoId = todo.todoId,
                    todoStatus = todo.todoStatus,
                    categoryName = TodoCategory.valueOf(todo.activityCategory).title,
                    categoryImage = TodoImageCategory.valueOf(todo.activityCategory).res
                )
            },
            nextTodosCnt = this.nextTodosCnt,
            successTodos = this.successTodos.map { todo ->
                Todo(
                    activityCategory = todo.activityCategory,
                    activityName = todo.activityName,
                    leftTime = todo.leftTime,
                    todoId = todo.todoId,
                    todoStatus = todo.todoStatus,
                    categoryName = TodoCategory.valueOf(todo.activityCategory).title,
                    categoryImage = TodoImageCategory.valueOf(todo.activityCategory).res
                )
            },
            successTodosCnt = this.successTodosCnt,
            thisWeekTodos = this.thisWeekTodos.map { todo ->
                Todo(
                    activityCategory = todo.activityCategory,
                    activityName = todo.activityName,
                    leftTime = todo.leftTime,
                    todoId = todo.todoId,
                    todoStatus = todo.todoStatus,
                    categoryName = TodoCategory.valueOf(todo.activityCategory).title,
                    categoryImage = TodoImageCategory.valueOf(todo.activityCategory).res
                )
            },
            thisWeekTodosCnt = this.thisWeekTodosCnt,
            todayTodos = this.todayTodos.map { todo ->
                Todo(
                    activityCategory = todo.activityCategory,
                    activityName = todo.activityName,
                    leftTime = todo.leftTime,
                    todoId = todo.todoId,
                    todoStatus = todo.todoStatus,
                    categoryName = TodoCategory.valueOf(todo.activityCategory).title,
                    categoryImage = TodoImageCategory.valueOf(todo.activityCategory).res
                )
            },
            todayTodosCnt = this.todayTodosCnt
        )
}
