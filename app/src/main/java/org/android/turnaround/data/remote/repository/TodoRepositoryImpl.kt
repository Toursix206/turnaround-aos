package org.android.turnaround.data.remote.repository

import org.android.turnaround.data.remote.datasource.TodoDataSource
import org.android.turnaround.domain.entity.TodoList
import javax.inject.Inject

class TodoRepositoryImpl @Inject constructor(
    private val todoDataSource: TodoDataSource
) : TodoRepository {
    override suspend fun getTodoList(): Result<TodoList> =
        kotlin.runCatching {
            todoDataSource.getTodoList()
        }.map { response ->
            response.data.toTodoList()
        }
}
