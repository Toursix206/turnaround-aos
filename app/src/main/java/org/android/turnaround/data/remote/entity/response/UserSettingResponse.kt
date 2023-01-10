package org.android.turnaround.data.remote.entity.response

import com.google.gson.annotations.SerializedName
import org.android.turnaround.domain.entity.OpenSourceLicenseUrl
import org.android.turnaround.domain.entity.UserSetting

data class UserSettingResponse(
    @SerializedName("agreeAllPushNotification")
    val isAgreeNotification: Boolean,
    val openSourceLicenseUrl: OpenSourceLicenseUrlEntity,
    @SerializedName("privateInformationPolicyUrl")
    val policyUrl: String
) {
    fun toUserSetting() =
        UserSetting(
            isAgreeNotification = this.isAgreeNotification,
            openSourceLicenseUrl = OpenSourceLicenseUrl(this.openSourceLicenseUrl.aos),
            policyUrl = this.policyUrl
        )
}

data class OpenSourceLicenseUrlEntity(
    val aos: String
)
