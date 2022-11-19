package org.android.turnaround.data.remote.repository

import org.android.turnaround.data.local.datasource.LocalAuthPrefDataSource
import org.android.turnaround.data.remote.datasource.RefreshDataSource
import org.android.turnaround.data.remote.entity.response.TokenEntity
import org.android.turnaround.domain.entity.RefreshToken
import org.android.turnaround.domain.repository.RefreshRepository
import javax.inject.Inject

class RefreshRepositoryImpl @Inject constructor(
    private val localAuthPrefDataSource: LocalAuthPrefDataSource,
    private val refreshDataSource: RefreshDataSource
) : RefreshRepository {
    override suspend fun refreshToken(): Result<RefreshToken> =
        kotlin.runCatching {
            refreshDataSource.refreshToken(
                TokenEntity(
                    accessToken = localAuthPrefDataSource.accessToken,
                    refreshToken = localAuthPrefDataSource.refreshToken
                )
            )
        }.map { response -> response.data.toRefreshToken() }

    companion object {
        const val EXPIRED_TOKEN = 401
    }
}
