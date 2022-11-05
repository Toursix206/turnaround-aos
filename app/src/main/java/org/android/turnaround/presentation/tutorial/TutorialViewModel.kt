package org.android.turnaround.presentation.tutorial

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kakao.sdk.auth.model.OAuthToken
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber

class TutorialViewModel : ViewModel() {
    private val _currentTutorial = MutableStateFlow(0)
    val currentTutorial: StateFlow<Int> = _currentTutorial.asStateFlow()

    private val _kakaoToken = MutableStateFlow("")
    val kakaToken: StateFlow<String> = _kakaoToken.asStateFlow()

    private val _isSuccessKakaoLogin = MutableSharedFlow<Boolean>()
    val isSuccessKakaoLogin: SharedFlow<Boolean> = _isSuccessKakaoLogin.asSharedFlow()

    val kakaoLoginCallback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error != null) {
            Timber.e(error, "kakao 로그인 실패")

        } else if (token != null) {
            Timber.d("kakao 로그인 성공 ${token.accessToken}")
            _kakaoToken.value = token.accessToken
            viewModelScope.launch { _isSuccessKakaoLogin.emit(true) }
        }
    }

    fun initCurrentTutorial(current: Int) {
        _currentTutorial.value = current
    }

    fun nextCurrentTutorial() {
        if (currentTutorial.value < 2) _currentTutorial.value++
    }
}
