package org.android.turnaround.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.android.turnaround.data.remote.repository.HomeRepository
import org.android.turnaround.domain.entity.Home
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository
) : ViewModel() {
    private val _isTodayTodoExist = MutableStateFlow(false)
    val isTodayTodoExist: StateFlow<Boolean> = _isTodayTodoExist.asStateFlow()

    private val _home = MutableLiveData<Home>()
    val home: LiveData<Home> = _home

    init {
        getHome()
    }

    private fun getHome() = viewModelScope.launch {
        kotlin.runCatching {
            homeRepository.getHome()
        }.onSuccess {
            _home.value = it.getOrNull()

            if ((it.getOrNull()?.todosCnt ?: 0) <= 0) {
                _isTodayTodoExist.value = true
            }
        }.onFailure {
            Timber.d(it.message)
        }
    }
}
