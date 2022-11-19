package org.android.turnaround.domain.repository

import org.android.turnaround.domain.entity.RefreshToken

interface RefreshRepository {
    suspend fun refreshToken(): Result<RefreshToken>
}
