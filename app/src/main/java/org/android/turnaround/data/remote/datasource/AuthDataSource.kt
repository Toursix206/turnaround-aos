package org.android.turnaround.data.remote.datasource

import org.android.turnaround.data.remote.entity.request.NicknameValidRequest
import org.android.turnaround.data.remote.entity.request.SignUpRequest
import org.android.turnaround.data.remote.entity.response.NoDataResponse
import org.android.turnaround.data.remote.service.AuthService
import javax.inject.Inject

class AuthDataSource @Inject constructor(
    private val authService: AuthService
) {
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
}
