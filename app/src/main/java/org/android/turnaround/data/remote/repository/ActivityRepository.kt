package org.android.turnaround.data.remote.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import org.android.turnaround.domain.entity.ActivityCategory
import org.android.turnaround.domain.entity.ActivityContent

interface ActivityRepository {
    suspend fun getActivities(
        category: ActivityCategory? = null,
        size: Int
    ): Flow<PagingData<ActivityContent>>
}
