package org.android.turnaround.data.remote.repository

import org.android.turnaround.data.remote.datasource.HomeDataSource
import org.android.turnaround.domain.entity.Home
import org.android.turnaround.domain.repository.HomeRepository
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val homeDataSource: HomeDataSource
) : HomeRepository {
    override suspend fun getHome(): Result<Home> =
        kotlin.runCatching {
            homeDataSource.getHome()
        }.map { response ->
            response.data.toHome()
        }
}
