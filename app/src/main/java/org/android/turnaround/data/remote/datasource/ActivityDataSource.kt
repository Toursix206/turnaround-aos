package org.android.turnaround.data.remote.datasource

import org.android.turnaround.data.remote.entity.request.PostReserveTodoRequest
import org.android.turnaround.data.remote.entity.response.ActivityResponse
import org.android.turnaround.data.remote.entity.response.BaseResponse
import org.android.turnaround.data.remote.entity.response.NoDataResponse
import org.android.turnaround.data.remote.entity.response.TodoGuideResponse
import org.android.turnaround.data.remote.service.ActivityService
import org.android.turnaround.domain.entity.ActivityCategory
import org.android.turnaround.domain.entity.PushStatusType
import javax.inject.Inject

class ActivityDataSource @Inject constructor(
    private val activityService: ActivityService
) {
    suspend fun getActivities(
        category: ActivityCategory? = null,
        page: Int,
        size: Int
    ): BaseResponse<ActivityResponse> =
        activityService.getActivities(
            category = category,
            page = page,
            size = size
        )

    suspend fun getTodoGuide(todoId: Int): BaseResponse<TodoGuideResponse> =
        activityService.getTodoGuide(todoId)

    suspend fun postReserveTodo(activityId: Int, pushStatus: PushStatusType, startAtDate: String, startAtTime: String): NoDataResponse =
        activityService.postReserveTodo(
            PostReserveTodoRequest(
                activityId = activityId,
                pushStatus = pushStatus,
                startAt = "$startAtDate$TIME_SEPARATION$startAtTime"
            )
        )

    companion object {
        const val TIME_SEPARATION = "T"
    }
}
