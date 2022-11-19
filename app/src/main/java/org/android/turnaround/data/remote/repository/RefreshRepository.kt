package org.android.turnaround.data.remote.repository

import org.android.turnaround.domain.entity.RefreshToken

interface RefreshRepository {
    suspend fun refreshToken(): Result<RefreshToken>
}
