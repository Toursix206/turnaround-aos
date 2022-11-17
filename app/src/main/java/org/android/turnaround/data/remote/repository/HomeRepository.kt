package org.android.turnaround.data.remote.repository

import org.android.turnaround.domain.entity.Home

interface HomeRepository {
    suspend fun getHome(): Result<Home>
}
