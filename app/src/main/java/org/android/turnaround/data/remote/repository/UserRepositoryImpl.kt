package org.android.turnaround.data.remote.repository

import org.android.turnaround.data.remote.datasource.UserDataSource
import org.android.turnaround.domain.entity.My
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDataSource: UserDataSource
) : UserRepository {
    override suspend fun getUser(): Result<My> =
        kotlin.runCatching {
            userDataSource.getUser()
        }.map { response ->
            response.data.toMy()
        }
}
