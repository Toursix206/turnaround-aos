package org.android.turnaround.data.remote.entity.response

import org.android.turnaround.domain.entity.Home
import org.android.turnaround.domain.entity.HomeTodo
import org.android.turnaround.domain.entity.TodoCategory
import org.android.turnaround.domain.entity.TodoType

data class HomeResponse(
    val nickname: String,
    val level: Int,
    val broom: Int,
    val cleanScore: Int,
    val todosCnt: Int,
    val todos: List<HomeTodoEntity>
) {
    fun toHome(): Home =
        Home(
            nickname = this.nickname,
            level = this.level,
            broom = this.broom,
            cleanScore = this.cleanScore,
            todosCnt = this.todosCnt,
            todos = this.todos.map { todo ->
                HomeTodo(
                    activityCategory = todo.activityCategory,
                    activityName = todo.activityName,
                    leftTime = todo.leftTime,
                    todoId = todo.todoId,
                    todoStatus = TodoType.valueOf(todo.todoStatus),
                    duration = todo.duration,
                    categoryName = TodoCategory.valueOf(todo.activityCategory).title,
                    categoryImage = TodoCategory.valueOf(todo.activityCategory).imgRes
                )
            }
        )
}

data class HomeTodoEntity(
    val activityCategory: String,
    val activityName: String,
    val leftTime: String,
    val todoId: Int,
    val todoStatus: String,
    val duration: Int
)
