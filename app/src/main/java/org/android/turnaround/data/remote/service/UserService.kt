package org.android.turnaround.data.remote.service

import org.android.turnaround.data.remote.entity.request.UserSettingRequest
import org.android.turnaround.data.remote.entity.response.*
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT

interface UserService {
    @GET("/v1/user")
    suspend fun getUser(): BaseResponse<MyResponse>

    @GET("/v1/user/setting")
    suspend fun getUserSetting(): BaseResponse<UserSettingResponse>

    @PUT("/v1/user/setting")
    suspend fun putUserSetting(@Body userSetting: UserSettingRequest): BaseResponse<String>
}
