package org.android.turnaround.data.remote.service

import org.android.turnaround.data.remote.entity.response.BaseResponse
import org.android.turnaround.data.remote.entity.response.RefreshTokenResponse
import org.android.turnaround.data.remote.entity.response.TokenEntity
import retrofit2.http.Body
import retrofit2.http.POST

interface RefreshService {
    @POST("/v1/auth/refresh")
    suspend fun postRefreshToken(
        @Body body: TokenEntity
    ): BaseResponse<RefreshTokenResponse>
}
