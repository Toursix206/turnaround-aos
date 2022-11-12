package org.android.turnaround.data.remote.repository

import org.android.turnaround.domain.entity.Login
import org.android.turnaround.domain.entity.SignUp
import org.android.turnaround.domain.entity.TodoList
import org.android.turnaround.domain.entity.Token

interface TodoRepository {
    suspend fun getTodoList(): Result<TodoList>
}
