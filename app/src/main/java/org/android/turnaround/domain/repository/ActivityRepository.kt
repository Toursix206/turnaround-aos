package org.android.turnaround.domain.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import org.android.turnaround.domain.entity.ActivityCategory
import org.android.turnaround.domain.entity.ActivityContent
import org.android.turnaround.domain.entity.TodoGuide

interface ActivityRepository {
    suspend fun getActivities(
        category: ActivityCategory? = null,
        size: Int
    ): Flow<PagingData<ActivityContent>>

    suspend fun getTodoGuide(activityId: Int): Result<TodoGuide>
}
