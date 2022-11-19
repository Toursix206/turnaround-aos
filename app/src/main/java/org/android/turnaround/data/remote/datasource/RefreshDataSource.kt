package org.android.turnaround.data.remote.datasource

import org.android.turnaround.data.remote.entity.response.BaseResponse
import org.android.turnaround.data.remote.entity.response.RefreshTokenResponse
import org.android.turnaround.data.remote.entity.response.TokenEntity
import org.android.turnaround.data.remote.service.AuthService
import javax.inject.Inject

class RefreshDataSource @Inject constructor(
    private val authService: AuthService
) {
    suspend fun refreshToken(body: TokenEntity): BaseResponse<RefreshTokenResponse> =
        authService.postRefreshToken(body)
}
