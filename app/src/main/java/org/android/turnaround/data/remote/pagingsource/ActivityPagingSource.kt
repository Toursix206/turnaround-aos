package org.android.turnaround.data.remote.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import org.android.turnaround.data.remote.datasource.ActivityDataSource
import org.android.turnaround.domain.entity.ActivityCategory
import org.android.turnaround.domain.entity.ActivityContent
import timber.log.Timber

class ActivityPagingSource(
    private val activityDataSource: ActivityDataSource,
    private val size: Int = 10,
    private val category: ActivityCategory? = null
) : PagingSource<Int, ActivityContent>() {
    private var currentPage: Int = FIRST_PAGE

    override fun getRefreshKey(state: PagingState<Int, ActivityContent>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ActivityContent> {
        return try {
            val newPage = params.key ?: FIRST_PAGE
            val activity =
                activityDataSource.getActivities(category = this.category, page = newPage, size = this.size).data.toActivityContent()
            val activityContents = activity.activityContents
            return LoadResult.Page(
                data = activityContents,
                prevKey = if (currentPage <= 0) null else currentPage - 1,
                nextKey = if (activity.nextPage == -1) null else currentPage + 1
            )
        } catch (e: Exception) {
            Timber.tag(this.javaClass.toString()).d(e.message.toString())
            LoadResult.Error(e)
        }
    }

    companion object {
        private const val FIRST_PAGE = 0
    }
}
