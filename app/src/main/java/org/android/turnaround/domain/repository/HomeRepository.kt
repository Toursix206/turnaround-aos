package org.android.turnaround.domain.repository

import org.android.turnaround.domain.entity.Home

interface HomeRepository {
    suspend fun getHome(): Result<Home>
}
