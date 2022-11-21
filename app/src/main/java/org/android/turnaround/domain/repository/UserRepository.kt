package org.android.turnaround.domain.repository

import org.android.turnaround.domain.entity.My
import org.android.turnaround.domain.entity.UserSetting

interface UserRepository {
    suspend fun getUser(): Result<My>
    suspend fun getUserSetting(): Result<UserSetting>
    suspend fun putUserSetting(isAgreeNotification: Boolean): Result<String>
}
