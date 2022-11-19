package org.android.turnaround.data.remote.datasource

import org.android.turnaround.data.remote.service.UserService
import javax.inject.Inject

class UserDataSource @Inject constructor(
    private val userService: UserService
) {
    suspend fun getUser() = userService.getUser()
}
