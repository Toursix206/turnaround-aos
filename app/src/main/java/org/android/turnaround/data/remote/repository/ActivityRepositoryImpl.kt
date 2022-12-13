package org.android.turnaround.data.remote.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import org.android.turnaround.data.remote.datasource.ActivityDataSource
import org.android.turnaround.data.remote.pagingsource.ActivityPagingSource
import org.android.turnaround.domain.entity.ActivityCategory
import org.android.turnaround.domain.entity.ActivityContent
import org.android.turnaround.domain.entity.TodoGuide
import org.android.turnaround.domain.repository.ActivityRepository
import javax.inject.Inject

class ActivityRepositoryImpl @Inject constructor(
    private val activityDataSource: ActivityDataSource
) : ActivityRepository {
    override suspend fun getActivities(
        category: ActivityCategory?,
        size: Int
    ): Flow<PagingData<ActivityContent>> =
        Pager(
            config = PagingConfig(pageSize = size, enablePlaceholders = false),
            pagingSourceFactory = {
                ActivityPagingSource(
                    activityDataSource = activityDataSource,
                    size = size,
                    category = category
                )
            }
        ).flow

    override suspend fun getTodoGuide(todoId: Int): Result<TodoGuide> =
        kotlin.runCatching { activityDataSource.getTodoGuide(todoId) }
            .map { response -> response.data.toTodoGuide() }
}
