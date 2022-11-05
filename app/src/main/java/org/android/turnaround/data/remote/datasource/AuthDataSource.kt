package org.android.turnaround.data.remote.datasource

import org.android.turnaround.data.remote.entity.request.NicknameValidRequest
import org.android.turnaround.data.remote.entity.response.NoDataResponse
import org.android.turnaround.data.remote.service.AuthService
import javax.inject.Inject

class AuthDataSource @Inject constructor(
    private val authService: AuthService
) {
    suspend fun postNicknameValid(nickname: String): NoDataResponse =
        authService.postNicknameValid(NicknameValidRequest(nickname = nickname))
}
