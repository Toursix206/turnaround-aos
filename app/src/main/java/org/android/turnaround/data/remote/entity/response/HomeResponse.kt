package org.android.turnaround.data.remote.entity.response

import org.android.turnaround.domain.entity.Home
import org.android.turnaround.domain.entity.Todo

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
            todos = toTodos(this.todos)
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
