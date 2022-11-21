package org.android.turnaround.presentation.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.android.turnaround.domain.repository.ActivityRepository
import org.android.turnaround.domain.entity.ActivityCategory
import org.android.turnaround.domain.entity.ActivityContent
import javax.inject.Inject

@HiltViewModel
class ActivityViewModel @Inject constructor(
    private val activityRepository: ActivityRepository
) : ViewModel() {
    private val _category = MutableStateFlow<ActivityCategory?>(null)
    val category: StateFlow<ActivityCategory?> = _category.asStateFlow()

    fun initCategory(category: ActivityCategory?) {
        _category.value = category
    }

    suspend fun getActivities(): Flow<PagingData<ActivityContent>> =
        activityRepository.getActivities(category = category.value, size = ACTIVITY_LIST_SIZE)
            .cachedIn(viewModelScope)

    companion object {
        const val ACTIVITY_LIST_SIZE = 10
    }
}
