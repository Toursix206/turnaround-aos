package org.android.turnaround.data.remote.repository

import org.android.turnaround.data.remote.datasource.ActivityDataSource
import org.android.turnaround.domain.entity.Activity
import org.android.turnaround.domain.entity.ActivityCategory
import org.android.turnaround.domain.entity.ActivityType
import javax.inject.Inject

class ActivityRepositoryImpl @Inject constructor(
    private val activityDataSource: ActivityDataSource
) : ActivityRepository {
    override suspend fun getActivities(
        category: ActivityCategory?,
        page: Int,
        size: Int,
        type: ActivityType
    ): Result<Activity> =
        kotlin.runCatching {
            activityDataSource.getActivities(
                category = category,
                page = page,
                size = size,
                type = type
            )
        }.map { response -> response.data.toActivity() }
}
