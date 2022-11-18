package org.android.turnaround.data.remote.datasource

import org.android.turnaround.data.remote.entity.request.TodoEditRequest
import org.android.turnaround.data.remote.service.TodoService
import javax.inject.Inject

class TodoDataSource @Inject constructor(
    private val todoService: TodoService
) {
    suspend fun getTodoList() = todoService.getTodoList()
    suspend fun getTodoDetail(todoId: Int) = todoService.getTodoDetail(todoId)
    suspend fun deleteTodo(todoId: Int) = todoService.deleteTodo(todoId)
    suspend fun putTodo(todoId: Int, body: TodoEditRequest) = todoService.putTodo(todoId, body)
    suspend fun putNotificationOff() = todoService.putNotificationOff()
}
