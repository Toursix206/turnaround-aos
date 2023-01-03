package org.android.turnaround.data.remote.service

import okhttp3.MultipartBody
import org.android.turnaround.data.remote.entity.request.ReviewRequest
import org.android.turnaround.data.remote.entity.request.TodoEditRequest
import org.android.turnaround.data.remote.entity.response.BaseResponse
import org.android.turnaround.data.remote.entity.response.NoDataResponse
import org.android.turnaround.data.remote.entity.response.NotWrittenReviewResponse
import org.android.turnaround.data.remote.entity.response.TodoCertificateResponse
import org.android.turnaround.data.remote.entity.response.TodoDetailResponse
import org.android.turnaround.data.remote.entity.response.TodoListResponse
import org.android.turnaround.data.remote.entity.response.TodoRewardResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

interface TodoService {
    @GET("/v1/todos")
    suspend fun getTodoList(): BaseResponse<TodoListResponse>

    @GET("/v1/todo/{todoId}")
    suspend fun getTodoDetail(
        @Path("todoId") todoId: Int
    ): BaseResponse<TodoDetailResponse>

    @PUT("/v1/todo/{todoId}")
    suspend fun putTodo(
        @Path("todoId") todoId: Int,
        @Body body: TodoEditRequest
    ): BaseResponse<String>

    @DELETE("/v1/todo/{todoId}")
    suspend fun deleteTodo(
        @Path("todoId") todoId: Int
    ): BaseResponse<String>

    @PUT("/v1/todo/{todoId}/reward")
    suspend fun putTodoReward(
        @Path("todoId") todoId: Int
    ): BaseResponse<TodoRewardResponse>

    @PUT("/v1/todos/notification/off")
    suspend fun putNotificationOff(): BaseResponse<String>

    @GET("/v1/todo/{todoId}/guide/able")
    suspend fun getTodoStartAble(
        @Path("todoId") todoId: Int
    ): NoDataResponse

    @Multipart
    @POST("/v1/todo/{todoId}/done")
    suspend fun postTodoCertificate(
        @Path("todoId") todoId: Int,
        @Part image: MultipartBody.Part
    ): BaseResponse<TodoCertificateResponse>

    @GET("/v1/todo/done/review/{doneReviewId}")
    suspend fun getNotWrittenReview(
        @Path("doneReviewId") doneReviewId: Int
    ): BaseResponse<NotWrittenReviewResponse>

    @POST("/v1/todo/done/review/{doneReviewId}")
    suspend fun postReview(
        @Path("doneReviewId") doneReviewId: Int,
        @Body body: ReviewRequest
    ): NoDataResponse
}
