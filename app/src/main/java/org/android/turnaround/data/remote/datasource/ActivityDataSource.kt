package org.android.turnaround.data.remote.datasource

import org.android.turnaround.data.remote.entity.response.ActivityResponse
import org.android.turnaround.data.remote.entity.response.BaseResponse
import org.android.turnaround.data.remote.service.ActivityService
import org.android.turnaround.domain.entity.ActivityCategory
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
}
