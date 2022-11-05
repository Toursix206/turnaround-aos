package org.android.turnaround.presentation.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.android.turnaround.data.remote.repository.AuthRepository
import org.android.turnaround.domain.entity.ProfileType
import retrofit2.HttpException
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _isSuccessSignUp = MutableSharedFlow<Boolean>()
    val isSuccessSignUp: SharedFlow<Boolean> = _isSuccessSignUp.asSharedFlow()

    private val _isNicknameValid = MutableStateFlow(false)
    val isNicknameValid: StateFlow<Boolean> = _isNicknameValid.asStateFlow()

    private val _isNicknameDuplicate = MutableStateFlow(false)
    val isNicknameDuplicate: StateFlow<Boolean> = _isNicknameDuplicate.asStateFlow()

    private val _selectedProfile = MutableStateFlow(ProfileType.ONE)
    val selectedProfile: StateFlow<ProfileType> = _selectedProfile.asStateFlow()

    val nickname = MutableStateFlow(EMPTY_NICKNAME)

    fun initSelectedProfile(type: ProfileType) {
        _selectedProfile.value = type
    }

    fun resetIsNicknameValid() {
        _isNicknameValid.value = false
    }

    fun resetIsNicknameDuplicate() {
        _isNicknameDuplicate.value = false
    }

    fun resetNickname() {
        nickname.value = EMPTY_NICKNAME
        resetIsNicknameValid()
        resetIsNicknameDuplicate()
    }

    fun postNicknameValid() {
        viewModelScope.launch {
            authRepository.postNicknameValid(nickname.value)
                .onSuccess {
                    _isNicknameValid.value = true
                    Timber.d(it.toString())
                }
                .onFailure { throwable ->
                    if (throwable is HttpException) {
                        when (throwable.code()) {
                            DUPLICATE_NICKNAME -> _isNicknameDuplicate.value = true
                        }
                    }
                    Timber.d(throwable.message)
                }
        }
    }

    fun postSignUp() {
        viewModelScope.launch {
            authRepository.postSignUp(nickname = nickname.value, profileType = selectedProfile.value.name)
                .onSuccess { _isSuccessSignUp.emit(true) }
                .onFailure {
                    Timber.d(it.message)
                }
        }
    }

    companion object {
        const val EMPTY_NICKNAME = ""
        const val DUPLICATE_NICKNAME = 409
    }
}
