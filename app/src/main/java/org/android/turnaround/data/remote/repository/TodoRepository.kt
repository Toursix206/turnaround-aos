package org.android.turnaround.data.remote.repository

import org.android.turnaround.domain.entity.TodoDetail
import org.android.turnaround.domain.entity.TodoList

interface TodoRepository {
    suspend fun getTodoList(): Result<TodoList>
    suspend fun  getTodoDetail(todoId: Int): Result<TodoDetail>
}
