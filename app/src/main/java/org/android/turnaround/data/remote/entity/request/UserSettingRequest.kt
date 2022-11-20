package org.android.turnaround.data.remote.entity.request

import com.google.gson.annotations.SerializedName

data class UserSettingRequest(
    @SerializedName("agreeAllPushNotification")
    val isAgreeNotification: Boolean
)
