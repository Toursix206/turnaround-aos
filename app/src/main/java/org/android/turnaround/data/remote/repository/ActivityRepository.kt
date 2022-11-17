package org.android.turnaround.data.remote.repository

import org.android.turnaround.domain.entity.Activity
import org.android.turnaround.domain.entity.ActivityCategory
import org.android.turnaround.domain.entity.ActivityType

interface ActivityRepository {
    suspend fun getActivities(
        category: ActivityCategory? = null,
        page: Int,
        size: Int,
        type: ActivityType
    ): Result<Activity>
}
