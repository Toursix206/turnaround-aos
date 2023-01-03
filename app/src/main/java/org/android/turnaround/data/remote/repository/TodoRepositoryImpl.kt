package org.android.turnaround.data.remote.repository

import okhttp3.MultipartBody
import org.android.turnaround.data.remote.datasource.TodoDataSource
import org.android.turnaround.data.remote.entity.request.TodoEditRequest
import org.android.turnaround.domain.entity.NotWrittenReview
import org.android.turnaround.domain.entity.TodoCertificate
import org.android.turnaround.domain.entity.TodoDetail
import org.android.turnaround.domain.entity.TodoList
import org.android.turnaround.domain.entity.TodoReward
import org.android.turnaround.domain.repository.TodoRepository
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

    override suspend fun getTodoDetail(todoId: Int): Result<TodoDetail> =
        kotlin.runCatching {
            todoDataSource.getTodoDetail(todoId)
        }.map { response ->
            response.data.toTodoDetail(todoId)
        }

    override suspend fun deleteTodo(todoId: Int): Result<String> =
        kotlin.runCatching { todoDataSource.deleteTodo(todoId) }
            .map { response -> response.data }

    override suspend fun putTodo(todoId: Int, body: TodoEditRequest): Result<String> =
        kotlin.runCatching { todoDataSource.putTodo(todoId, body) }
            .map { response -> response.data }

    override suspend fun putTodoReward(todoId: Int): Result<TodoReward> =
        kotlin.runCatching { todoDataSource.putTodoReward(todoId) }
            .map { response -> response.data.toTodoReward() }

    override suspend fun putNotificationOff(): Result<String> =
        kotlin.runCatching { todoDataSource.putNotificationOff() }
            .map { response -> response.data }

    override suspend fun getTodoStartAble(todoId: Int): Result<Boolean> =
        kotlin.runCatching { todoDataSource.getTodoStartAble(todoId) }
            .map { response -> response.success }

    override suspend fun postTodoCertificate(todoId: Int, image: MultipartBody.Part): Result<TodoCertificate> =
        kotlin.runCatching { todoDataSource.postTodoCertificate(todoId, image) }
            .map { response -> response.data.toTodoCertificate() }

    override suspend fun getNotWrittenReview(doneReviewId: Int): Result<NotWrittenReview> =
        kotlin.runCatching { todoDataSource.getNotWrittenReview(doneReviewId) }
            .map { response -> response.data.toNotWrittenReview() }
}
