package org.android.turnaround.data.remote.service

import org.android.turnaround.data.remote.entity.request.TodoEditRequest
import org.android.turnaround.data.remote.entity.response.*
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface TodoService {
    @GET("/v1/todos") // 동 이벤트 페이지 - 활동 일정을 조회합니다.
    suspend fun getTodoList(): BaseResponse<TodoListResponse>

    @GET("/v1/todo/{todoId}") // 활동 이벤트 페이지 - 활동 세부 내용을 조회합니다.
    suspend fun getTodoDetail(@Path("todoId") todoId: Int): BaseResponse<TodoDetailResponse>

//    @POST("/v1/to do") // 활동 페이지 - 활동을 예약합니다.
//
    @PUT("/v1/todo/{todoId}") // 활동 이벤트 페이지 - 활동 일정을 수정합니다.
    suspend fun putTodo(@Path("todoId") todoId: Int, @Body body: TodoEditRequest): BaseResponse<String>

    @DELETE("/v1/todo/{todoId}") // 활동 이벤트 페이지 - 활동 일정을 삭제합니다.
    suspend fun deleteTodo(@Path("todoId") todoId: Int): BaseResponse<String>
//
//    @POST("/v1/to do/{todoId}/done") // 활동 인증 페이지 - 활동을 인증합니다.
//
//    @POST("/v1/to do/{todoId}/review") // 활동 리뷰 작성 페이지 - 활동을 리뷰를 작성합니다.
//
//    @PUT("/v1/to do/{todoId}/reward") // 활동 이벤트 페이지 - 완료된 활동의 리워드를 받습니다.
//
    @PUT("/v1/todos/notification/off") // 활동 이벤트 페이지 - 예약된 모든 활동의 알림을 끕니다.
    suspend fun putNotificationOff(): BaseResponse<String>
}
