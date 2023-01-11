package org.android.turnaround.data.remote.repository

import org.android.turnaround.data.remote.datasource.UserDataSource
import org.android.turnaround.domain.entity.My
import org.android.turnaround.domain.entity.UserSetting
import org.android.turnaround.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDataSource: UserDataSource
) : UserRepository {
    override suspend fun getMyPage(): Result<My> =
        kotlin.runCatching {
            userDataSource.getMyPage()
        }.map { response ->
            response.data.toMy()
        }

    override suspend fun getUserSetting(): Result<UserSetting> =
        kotlin.runCatching {
            userDataSource.getUserSetting()
        }.map { response ->
            response.data.toUserSetting()
        }

    override suspend fun putUserSetting(isAgreeNotification: Boolean): Result<String> =
        kotlin.runCatching {
            userDataSource.putUserSetting(isAgreeNotification)
        }.map { response ->
            response.data
        }
}
