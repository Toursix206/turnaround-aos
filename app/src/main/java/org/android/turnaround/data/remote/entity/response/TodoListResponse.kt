package org.android.turnaround.data.remote.entity.response

import org.android.turnaround.domain.entity.PushStatusType
import org.android.turnaround.domain.entity.Todo
import org.android.turnaround.domain.entity.TodoCategory
import org.android.turnaround.domain.entity.TodoList
import org.android.turnaround.domain.entity.TodoType

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
                    todoStatus = TodoType.valueOf(todo.todoStatus),
                    duration = todo.duration,
                    categoryName = TodoCategory.valueOf(todo.activityCategory).title,
                    categoryImage = TodoCategory.valueOf(todo.activityCategory).imgRes,
                    pushStatus = PushStatusType.valueOf(todo.pushStatus)
                )
            },
            nextTodosCnt = this.nextTodosCnt,
            successTodos = this.successTodos.map { todo ->
                Todo(
                    activityCategory = todo.activityCategory,
                    activityName = todo.activityName,
                    leftTime = todo.leftTime,
                    todoId = todo.todoId,
                    todoStatus = TodoType.valueOf(todo.todoStatus),
                    duration = todo.duration,
                    categoryName = TodoCategory.valueOf(todo.activityCategory).title,
                    categoryImage = TodoCategory.valueOf(todo.activityCategory).imgRes,
                    pushStatus = PushStatusType.valueOf(todo.pushStatus)
                )
            },
            successTodosCnt = this.successTodosCnt,
            thisWeekTodos = this.thisWeekTodos.map { todo ->
                Todo(
                    activityCategory = todo.activityCategory,
                    activityName = todo.activityName,
                    leftTime = todo.leftTime,
                    todoId = todo.todoId,
                    todoStatus = TodoType.valueOf(todo.todoStatus),
                    duration = todo.duration,
                    categoryName = TodoCategory.valueOf(todo.activityCategory).title,
                    categoryImage = TodoCategory.valueOf(todo.activityCategory).imgRes,
                    pushStatus = PushStatusType.valueOf(todo.pushStatus)
                )
            },
            thisWeekTodosCnt = this.thisWeekTodosCnt,
            todayTodos = this.todayTodos.map { todo ->
                Todo(
                    activityCategory = todo.activityCategory,
                    activityName = todo.activityName,
                    leftTime = todo.leftTime,
                    todoId = todo.todoId,
                    todoStatus = TodoType.valueOf(todo.todoStatus),
                    duration = todo.duration,
                    categoryName = TodoCategory.valueOf(todo.activityCategory).title,
                    categoryImage = TodoCategory.valueOf(todo.activityCategory).imgRes,
                    pushStatus = PushStatusType.valueOf(todo.pushStatus)
                )
            },
            todayTodosCnt = this.todayTodosCnt
        )
}

data class TodoEntity(
    val activityCategory: String,
    val activityName: String,
    val leftTime: String,
    val todoId: Int,
    val todoStatus: String,
    val duration: Int,
    val pushStatus: String
)
