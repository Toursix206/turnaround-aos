package org.android.turnaround.data.remote.service

import org.android.turnaround.data.remote.entity.response.*
import retrofit2.http.GET

interface UserService {
    @GET("/v1/user")
    suspend fun getUser(): BaseResponse<MyResponse>

    @GET("/v1/user/setting")
    suspend fun getUserSetting(): BaseResponse<UserSettingResponse>
}
