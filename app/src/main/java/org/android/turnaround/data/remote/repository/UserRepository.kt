package org.android.turnaround.data.remote.repository

import org.android.turnaround.domain.entity.My

interface UserRepository {
    suspend fun getUser(): Result<My>
}
