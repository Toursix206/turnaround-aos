package org.android.turnaround.data.remote.service

import org.android.turnaround.data.remote.entity.request.TodoEditRequest
import org.android.turnaround.data.remote.entity.response.*
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface TodoService {
    @GET("/v1/todos")
    suspend fun getTodoList(): BaseResponse<TodoListResponse>
    @GET("/v1/todo/{todoId}")
    suspend fun getTodoDetail(@Path("todoId") todoId: Int): BaseResponse<TodoDetailResponse>
    @PUT("/v1/todo/{todoId}")
    suspend fun putTodo(@Path("todoId") todoId: Int, @Body body: TodoEditRequest): BaseResponse<String>
    @DELETE("/v1/todo/{todoId}")
    suspend fun deleteTodo(@Path("todoId") todoId: Int): BaseResponse<String>
    @PUT("/v1/todos/notification/off")
    suspend fun putNotificationOff(): BaseResponse<String>
}
