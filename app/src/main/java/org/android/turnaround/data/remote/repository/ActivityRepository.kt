package org.android.turnaround.data.remote.repository

import org.android.turnaround.domain.entity.Activity
import org.android.turnaround.domain.entity.ActivityCategory

interface ActivityRepository {
    suspend fun getActivities(
        category: ActivityCategory? = null,
        page: Int,
        size: Int
    ): Result<Activity>
}
