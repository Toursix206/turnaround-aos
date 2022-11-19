package org.android.turnaround.presentation.my

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.android.turnaround.data.remote.repository.UserRepository
import org.android.turnaround.domain.entity.My
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MyViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {
    private val _my = MutableLiveData<My>()
    val my: LiveData<My> = _my

    init {
        getUser()
    }

    private fun getUser() = viewModelScope.launch {
        userRepository.getUser()
            .onSuccess {
                _my.value = it
            }.onFailure {
                Timber.d(it.message)
            }
    }
}
