package org.android.turnaround.presentation.signup

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SignUpViewModel : ViewModel() {
    private val _isNicknameValid = MutableStateFlow(false)
    val isNicknameValid: StateFlow<Boolean> = _isNicknameValid.asStateFlow()

    private val _selectedProfile = MutableStateFlow(0)
    val selectedProfile: StateFlow<Int> = _selectedProfile.asStateFlow()

    val nickname = MutableStateFlow(EMPTY_NICKNAME)
    //val nickname: StateFlow<String> = _nickname

    fun initSelectedProfile(index: Int) {
        _selectedProfile.value = index
    }

    fun resetIsNicknameValid() {
        _isNicknameValid.value = false
    }

    fun resetNickname() {
        nickname.value = EMPTY_NICKNAME
        resetIsNicknameValid()
    }

    fun postNicknameValid() {
        //_nickname.value = nickname
        _isNicknameValid.value = true
    }

    companion object {
        const val EMPTY_NICKNAME = ""
    }
}
