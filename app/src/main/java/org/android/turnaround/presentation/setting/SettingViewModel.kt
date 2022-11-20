package org.android.turnaround.presentation.setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.android.turnaround.data.remote.repository.UserRepository
import org.android.turnaround.domain.entity.UserSetting
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {
    private val _setting = MutableLiveData<UserSetting>()
    val setting: LiveData<UserSetting> = _setting

    init {
        getUserSetting()
    }

    private fun getUserSetting() = viewModelScope.launch {
        userRepository.getUserSetting()
            .onSuccess {
                _setting.value = it
            }.onFailure {
                Timber.d(it.message)
            }
    }

    fun putUserSetting(isAgreeNotification: Boolean) = viewModelScope.launch {
        userRepository.putUserSetting(isAgreeNotification)
            .onFailure {
                Timber.d(it.message)
            }
    }
}
