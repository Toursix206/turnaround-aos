package org.android.turnaround.presentation.setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import org.android.turnaround.domain.entity.UserSetting
import org.android.turnaround.domain.repository.AuthRepository
import org.android.turnaround.domain.repository.UserRepository
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository
) : ViewModel() {
    private val _setting = MutableLiveData<UserSetting>()
    val setting: LiveData<UserSetting> = _setting

    private val _isSuccessLogout = MutableSharedFlow<Boolean>()
    val isSuccessLogout: SharedFlow<Boolean> = _isSuccessLogout.asSharedFlow()

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

    fun postLogout() {
        viewModelScope.launch {
            authRepository.postLogout()
                .onSuccess {
                    authRepository.clearLocalPref()
                    _isSuccessLogout.emit(true)
                }
                .onFailure { Timber.d(it.message.toString()) }
        }
    }
}
