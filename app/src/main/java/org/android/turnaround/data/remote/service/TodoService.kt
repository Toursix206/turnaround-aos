package org.android.turnaround.data.remote.service

import org.android.turnaround.data.remote.entity.response.*
import retrofit2.http.GET

interface TodoService {
    @GET("/v1/todos")
    suspend fun getTodoList(): BaseResponse<TodoListResponse>
}
