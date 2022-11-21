package org.android.turnaround.data.remote.datasource

import android.content.SharedPreferences
import org.android.turnaround.data.remote.entity.request.LoginRequest
import org.android.turnaround.data.remote.entity.request.NicknameValidRequest
import org.android.turnaround.data.remote.entity.request.SignUpRequest
import org.android.turnaround.data.remote.entity.response.BaseResponse
import org.android.turnaround.data.remote.entity.response.LoginResponse
import org.android.turnaround.data.remote.entity.response.NoDataResponse
import org.android.turnaround.data.remote.service.AuthService
import javax.inject.Inject

class AuthDataSource @Inject constructor(
    private val authService: AuthService,
    private val localPrefs: SharedPreferences
) {
    fun clearLocalPref() {
        with(localPrefs.edit()) {
            clear()
            commit()
        }
    }

    suspend fun postNicknameValid(nickname: String): NoDataResponse =
        authService.postNicknameValid(NicknameValidRequest(nickname = nickname))

    suspend fun postSignUp(nickname: String, profileType: String, fcmToken: String, socialType: String, token: String) =
        authService.postSignUp(
            SignUpRequest(
                nickname = nickname,
                profileType = profileType,
                fcmToken = fcmToken,
                socialType = socialType,
                token = token
            )
        )

    suspend fun postLogin(fcmToken: String, socialType: String, token: String): BaseResponse<LoginResponse> =
        authService.postLogin(
            LoginRequest(
                fcmToken = fcmToken,
                socialType = socialType,
                token = token
            )
        )

    suspend fun postLogout(): NoDataResponse = authService.postLogout()

    suspend fun deleteUser(): NoDataResponse = authService.deleteUser()
}
