package org.android.turnaround.data.remote.service

import org.android.turnaround.data.remote.entity.request.LoginRequest
import org.android.turnaround.data.remote.entity.request.NicknameValidRequest
import org.android.turnaround.data.remote.entity.request.SignUpRequest
import org.android.turnaround.data.remote.entity.response.BaseResponse
import org.android.turnaround.data.remote.entity.response.LoginResponse
import org.android.turnaround.data.remote.entity.response.NoDataResponse
import org.android.turnaround.data.remote.entity.response.SignUpResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.POST
interface AuthService {
    @POST("/v1/user/nickname/check")
    suspend fun postNicknameValid(
        @Body body: NicknameValidRequest
    ): NoDataResponse

    @POST("/v1/auth/signup")
    suspend fun postSignUp(
        @Body body: SignUpRequest
    ): BaseResponse<SignUpResponse>

    @POST("/v1/auth/login")
    suspend fun postLogin(
        @Body body: LoginRequest
    ): BaseResponse<LoginResponse>

    @POST("/v1/auth/login/force")
    suspend fun postForceLogin(
        @Body body: LoginRequest
    ): BaseResponse<LoginResponse>

    @POST("/v1/auth/logout")
    suspend fun postLogout(): NoDataResponse

    @DELETE("/v1/user")
    suspend fun deleteUser(): NoDataResponse
}
