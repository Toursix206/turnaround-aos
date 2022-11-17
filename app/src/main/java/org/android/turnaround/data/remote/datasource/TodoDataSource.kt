package org.android.turnaround.data.remote.datasource

import org.android.turnaround.data.remote.service.TodoService
import javax.inject.Inject

class TodoDataSource @Inject constructor(
    private val todoService: TodoService
) {
    suspend fun getTodoList() = todoService.getTodoList()
    suspend fun getTodoDetail(todoId: Int) = todoService.getTodoDetail(todoId)
}
