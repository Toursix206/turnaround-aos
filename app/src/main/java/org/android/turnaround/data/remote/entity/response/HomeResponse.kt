package org.android.turnaround.data.remote.entity.response

import org.android.turnaround.domain.entity.Home
import org.android.turnaround.domain.entity.Todo
import org.android.turnaround.domain.entity.TodoCategory
import org.android.turnaround.domain.entity.TodoImageCategory

data class HomeResponse(
    val nickname: String,
    val level: Int,
    val broom: Int,
    val cleanScore: Int,
    val todosCnt: Int,
    val todos: List<TodoEntity>
) {
    fun toHome(): Home =
        Home(
            nickname = this.nickname,
            level = this.level,
            broom = this.broom,
            cleanScore = this.cleanScore,
            todosCnt = this.todosCnt,
            todos = this.todos.map { todo ->
                Todo(
                    activityCategory = todo.activityCategory,
                    activityName = todo.activityName,
                    leftTime = todo.leftTime,
                    todoId = todo.todoId,
                    todoStatus = todo.todoStatus,
                    categoryName = TodoCategory.valueOf(todo.activityCategory).title,
                    categoryImage = TodoImageCategory.valueOf(todo.activityCategory).res
                )
            }
        )
}