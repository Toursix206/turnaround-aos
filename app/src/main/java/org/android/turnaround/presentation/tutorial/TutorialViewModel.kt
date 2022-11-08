package org.android.turnaround.presentation.tutorial

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kakao.sdk.auth.model.OAuthToken
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import org.android.turnaround.data.remote.repository.AuthRepository
import retrofit2.HttpException
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class TutorialViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _currentTutorial = MutableStateFlow(0)
    val currentTutorial: StateFlow<Int> = _currentTutorial.asStateFlow()

    private val isSuccessKakaoLogin = MutableStateFlow(false)

    private val isSuccessInitFcmToken = MutableStateFlow(false)

    val isReadyToLogin =
        combine(isSuccessKakaoLogin, isSuccessInitFcmToken) { kakaoLogin, fcmToken -> kakaoLogin && fcmToken }

    private val _isSuccessLogin = MutableSharedFlow<Boolean>()
    val isSuccessLogin: SharedFlow<Boolean> = _isSuccessLogin.asSharedFlow()

    var failLoginStatusCode: Int = -1
        private set

    val kakaoLoginCallback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error != null) {
            Timber.e(error, "kakao 로그인 실패")
        } else if (token != null) {
            Timber.d("kakao 로그인 성공 ${token.accessToken}")
            authRepository.initKakaoToken(token.accessToken) { isSuccessKakaoLogin.value = true }
            authRepository.initFcmToken { isInit -> isSuccessInitFcmToken.value = isInit }
        }
    }

    fun initCurrentTutorial(current: Int) {
        _currentTutorial.value = current
    }

    fun nextCurrentTutorial() {
        if (currentTutorial.value < 2) _currentTutorial.value++
    }

    fun postLogin() {
        viewModelScope.launch {
            authRepository.postLogin()
                .onSuccess { response ->
                    authRepository.initTurnAroundToken(response.token)
                    _isSuccessLogin.emit(true)
                }
                .onFailure { throwable ->
                    Timber.d(throwable.message)
                    if (throwable is HttpException) {
                        when (throwable.code()) {
                            NOT_VALID_SOCIAL_TOKEN -> failLoginStatusCode = NOT_VALID_SOCIAL_TOKEN
                            NOT_USER -> failLoginStatusCode = NOT_USER
                            DUPLICATE_LOGIN -> failLoginStatusCode = DUPLICATE_LOGIN
                        }
                    }
                    _isSuccessLogin.emit(false)
                }
        }
    }

    companion object {
        const val NOT_VALID_SOCIAL_TOKEN = 401
        const val NOT_USER = 404
        const val DUPLICATE_LOGIN = 409
    }
}
