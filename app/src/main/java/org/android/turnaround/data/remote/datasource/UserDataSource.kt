package org.android.turnaround.data.remote.datasource

import org.android.turnaround.data.remote.entity.request.UserSettingRequest
import org.android.turnaround.data.remote.service.UserService
import javax.inject.Inject

class UserDataSource @Inject constructor(
    private val userService: UserService
) {
    suspend fun getMyPage() = userService.getMyPage()
    suspend fun getUserSetting() = userService.getUserSetting()
    suspend fun putUserSetting(isAgreeNotification: Boolean) = userService.putUserSetting(
        UserSettingRequest(
            isAgreeNotification = isAgreeNotification
        )
    )
}
