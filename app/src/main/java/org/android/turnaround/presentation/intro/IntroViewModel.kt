package org.android.turnaround.presentation.intro

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.android.turnaround.data.remote.repository.AuthRepository
import javax.inject.Inject

@HiltViewModel
class IntroViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _isUser = MutableStateFlow(false)
    val isUser: StateFlow<Boolean> = _isUser.asStateFlow()

    fun checkIsUser() {
        _isUser.value = authRepository.checkIsUser()
        //viewModelScope.launch { _isUser.emit(authRepository.checkIsUser()) }
    }
}
