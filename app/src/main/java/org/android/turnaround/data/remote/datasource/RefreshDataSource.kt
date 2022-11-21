package org.android.turnaround.data.remote.datasource

import org.android.turnaround.data.remote.entity.response.BaseResponse
import org.android.turnaround.data.remote.entity.response.RefreshTokenResponse
import org.android.turnaround.data.remote.entity.response.TokenEntity
import org.android.turnaround.data.remote.service.RefreshService
import javax.inject.Inject

class RefreshDataSource @Inject constructor(
    private val refreshService: RefreshService
) {
    suspend fun refreshToken(body: TokenEntity): BaseResponse<RefreshTokenResponse> =
        refreshService.postRefreshToken(body)
}
