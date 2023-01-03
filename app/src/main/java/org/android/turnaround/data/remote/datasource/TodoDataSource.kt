package org.android.turnaround.data.remote.datasource

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
import org.android.turnaround.data.remote.service.TodoService
import javax.inject.Inject

class TodoDataSource @Inject constructor(
    private val todoService: TodoService
) {
    suspend fun getTodoList(): BaseResponse<TodoListResponse> =
        todoService.getTodoList()

    suspend fun getTodoDetail(todoId: Int): BaseResponse<TodoDetailResponse> =
        todoService.getTodoDetail(todoId)

    suspend fun deleteTodo(todoId: Int): BaseResponse<String> =
        todoService.deleteTodo(todoId)

    suspend fun putTodo(todoId: Int, body: TodoEditRequest): BaseResponse<String> =
        todoService.putTodo(todoId, body)

    suspend fun putTodoReward(todoId: Int): BaseResponse<TodoRewardResponse> =
        todoService.putTodoReward(todoId)

    suspend fun putNotificationOff(): BaseResponse<String> =
        todoService.putNotificationOff()

    suspend fun getTodoStartAble(todoId: Int): NoDataResponse =
        todoService.getTodoStartAble(todoId)

    suspend fun postTodoCertificate(todoId: Int, image: MultipartBody.Part): BaseResponse<TodoCertificateResponse> =
        todoService.postTodoCertificate(todoId, image)

    suspend fun getNotWrittenReview(doneReviewId: Int): BaseResponse<NotWrittenReviewResponse> =
        todoService.getNotWrittenReview(doneReviewId)

    suspend fun postReview(doneReviewId: Int, content: String, rating: Int): NoDataResponse =
        todoService.postReview(doneReviewId, ReviewRequest(content = content, rating = rating))
}
