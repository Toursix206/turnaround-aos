package org.android.turnaround.data.remote.service

import org.android.turnaround.data.remote.entity.response.ActivityResponse
import org.android.turnaround.data.remote.entity.response.BaseResponse
import org.android.turnaround.domain.entity.ActivityCategory
import org.android.turnaround.domain.entity.ActivityType
import retrofit2.http.GET
import retrofit2.http.Query

interface ActivityService {
    @GET("/v1/activities")
    fun getActivities(
        @Query("category") category: ActivityCategory? = null,
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("type") type: ActivityType,
        @Query("sort") sort: Array<String> = arrayOf("createdAt", "DESC")
    ): BaseResponse<ActivityResponse>
}
