package org.android.turnaround.data.remote.datasource

import org.android.turnaround.data.remote.service.HomeService
import javax.inject.Inject

class HomeDataSource @Inject constructor(
    private val homeService: HomeService
) {
    suspend fun getHome() = homeService.getHome()
}
