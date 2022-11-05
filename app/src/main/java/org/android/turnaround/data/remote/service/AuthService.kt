package org.android.turnaround.data.remote.service

import org.android.turnaround.data.remote.entity.request.NicknameValidRequest
import org.android.turnaround.data.remote.entity.response.NoDataResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("/v1/user/nickname/check")
    suspend fun postNicknameValid(
        @Body body: NicknameValidRequest
    ): NoDataResponse
}
