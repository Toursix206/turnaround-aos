package org.android.turnaround.data.remote.service

import org.android.turnaround.data.remote.entity.response.*
import retrofit2.http.GET

interface HomeService {
    @GET("/v1/home")
    suspend fun getHome(): BaseResponse<HomeResponse>
}
