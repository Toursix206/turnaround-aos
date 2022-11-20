package org.android.turnaround.data.remote.datasource

import org.android.turnaround.data.local.datasource.LocalAuthPrefDataSource
import org.android.turnaround.data.remote.RefreshRetrofitBuilder
import org.android.turnaround.data.remote.entity.response.BaseResponse
import org.android.turnaround.data.remote.entity.response.RefreshTokenResponse
import org.android.turnaround.data.remote.entity.response.TokenEntity
import javax.inject.Inject

class RefreshDataSource @Inject constructor(
    private val localAuthPrefDataSource: LocalAuthPrefDataSource
) {
    suspend fun refreshToken(body: TokenEntity): BaseResponse<RefreshTokenResponse> =
        RefreshRetrofitBuilder(localAuthPrefDataSource).refreshService.postRefreshToken(body)
}
