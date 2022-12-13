package org.android.turnaround.data.remote.service

import org.android.turnaround.data.remote.entity.response.ActivityResponse
import org.android.turnaround.data.remote.entity.response.BaseResponse
import org.android.turnaround.data.remote.entity.response.TodoGuideResponse
import org.android.turnaround.domain.entity.ActivityCategory
import org.android.turnaround.domain.entity.ActivityType
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ActivityService {
    @GET("/v1/activities")
    suspend fun getActivities(
        @Query("category") category: ActivityCategory? = null,
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("type") type: ActivityType = ActivityType.FREE,
        @Query("sort") sort: Array<String> = arrayOf("createdAt", "DESC")
    ): BaseResponse<ActivityResponse>

    @GET("/v1/todo/{todoId}/guide")
    suspend fun getTodoGuide(
        @Path("todoId") todoId: Int
    ): BaseResponse<TodoGuideResponse>
}
