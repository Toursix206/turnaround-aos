package org.android.turnaround.data.local.datasource

import android.content.SharedPreferences
import androidx.core.content.edit
import javax.inject.Inject

class LocalAuthPrefDataSource @Inject constructor(
    private val prefs: SharedPreferences
) {
    var accessToken: String
        set(value) = prefs.edit { putString(ACCESS_TOKEN, value) }
        get() = prefs.getString(ACCESS_TOKEN, "") ?: ""

    var socialType: String
        set(value) = prefs.edit { putString(SOCIAL_TYPE, value) }
        get() = prefs.getString(SOCIAL_TYPE, "") ?: ""

    var kakaoToken: String
        set(value) = prefs.edit { putString(KAKAO_TOKEN, value) }
        get() = prefs.getString(KAKAO_TOKEN, "") ?: ""

    companion object {
        const val ACCESS_TOKEN = "accessToken"
        const val SOCIAL_TYPE = "socialType"
        const val KAKAO_TOKEN = "kakaoToken"
    }
}
