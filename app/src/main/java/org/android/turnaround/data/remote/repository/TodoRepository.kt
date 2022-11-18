package org.android.turnaround.data.remote.repository

import org.android.turnaround.data.remote.entity.request.TodoEditRequest
import org.android.turnaround.domain.entity.TodoDetail
import org.android.turnaround.domain.entity.TodoList

interface TodoRepository {
    suspend fun getTodoList(): Result<TodoList>
    suspend fun getTodoDetail(todoId: Int): Result<TodoDetail>
    suspend fun deleteTodo(todoId: Int): Result<String>
    suspend fun putTodo(todoId: Int, body: TodoEditRequest): Result<String>
    suspend fun putNotificationOff(): Result<String>
}
