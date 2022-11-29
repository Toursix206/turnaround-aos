package org.android.turnaround.domain.repository

import org.android.turnaround.data.remote.entity.request.TodoEditRequest
import org.android.turnaround.domain.entity.TodoDetail
import org.android.turnaround.domain.entity.TodoList
import org.android.turnaround.domain.entity.TodoReward

interface TodoRepository {
    suspend fun getTodoList(): Result<TodoList>
    suspend fun getTodoDetail(todoId: Int): Result<TodoDetail>
    suspend fun deleteTodo(todoId: Int): Result<String>
    suspend fun putTodo(todoId: Int, body: TodoEditRequest): Result<String>
    suspend fun putTodoReward(todoId: Int): Result<TodoReward>
    suspend fun putNotificationOff(): Result<String>
}
