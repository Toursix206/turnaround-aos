package org.android.turnaround.data.remote.entity.response

import com.google.gson.annotations.SerializedName
import org.android.turnaround.domain.entity.UserSetting

data class UserSettingResponse(
    @SerializedName("agreeAllPushNotification")
    val isAgreeNotification: Boolean
) {
    fun toUserSetting() =
        UserSetting(
            isAgreeNotification = this.isAgreeNotification
        )
}
