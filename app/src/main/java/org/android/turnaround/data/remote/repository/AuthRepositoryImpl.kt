package org.android.turnaround.data.remote.repository

import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import org.android.turnaround.data.local.datasource.LocalAuthPrefDataSource
import org.android.turnaround.data.remote.datasource.AuthDataSource
import org.android.turnaround.data.remote.datasource.UserDataSource
import org.android.turnaround.domain.entity.Login
import org.android.turnaround.domain.entity.SignUp
import org.android.turnaround.domain.entity.Token
import org.android.turnaround.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val localAuthPrefDataSource: LocalAuthPrefDataSource,
    private val authDataSource: AuthDataSource,
    private val userDataSource: UserDataSource
) : AuthRepository {
    override fun checkIsUser(): Boolean =
        localAuthPrefDataSource.accessToken.isNotBlank()

    override fun initFcmToken(isInitFcmToken: (Boolean) -> Unit) {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(
            OnCompleteListener { task ->
                localAuthPrefDataSource.fcmToken = task.result
                isInitFcmToken(true)
            }
        )
    }

    override fun initKakaoToken(kakaoToken: String, isInitKakaoToken: (Boolean) -> Unit) {
        localAuthPrefDataSource.socialType = SOCIAL_TYPE_KAKAO
        localAuthPrefDataSource.kakaoToken = kakaoToken
        isInitKakaoToken(true)
    }

    override fun initTurnAroundToken(token: Token) {
        localAuthPrefDataSource.accessToken = token.accessToken
        localAuthPrefDataSource.refreshToken = token.refreshToken
    }

    override fun clearLocalPref() {
        authDataSource.clearLocalPref()
    }

    override suspend fun postNicknameValid(nickname: String): Result<Boolean> =
        kotlin.runCatching { authDataSource.postNicknameValid(nickname) }
            .map { response -> response.success }

    override suspend fun postSignUp(
        nickname: String,
        profileType: String
    ): Result<SignUp> =
        kotlin.runCatching {
            authDataSource.postSignUp(
                nickname = nickname,
                profileType = profileType,
                fcmToken = localAuthPrefDataSource.fcmToken,
                socialType = localAuthPrefDataSource.socialType,
                token = localAuthPrefDataSource.kakaoToken
            )
        }.map { response ->
            response.data.toSignUp()
        }

    override suspend fun postLogin(): Result<Login> =
        kotlin.runCatching {
            authDataSource.postLogin(
                fcmToken = localAuthPrefDataSource.fcmToken,
                socialType = localAuthPrefDataSource.socialType,
                token = localAuthPrefDataSource.kakaoToken
            )
        }.map { response ->
            response.data.toLogin()
        }

    override suspend fun postForceLogin(): Result<Login> =
        kotlin.runCatching {
            authDataSource.postForceLogin(
                fcmToken = localAuthPrefDataSource.fcmToken,
                socialType = localAuthPrefDataSource.socialType,
                token = localAuthPrefDataSource.kakaoToken
            )
        }.map { response ->
            response.data.toLogin()
        }

    override suspend fun postLogout(): Result<Boolean> =
        kotlin.runCatching {
            authDataSource.postLogout()
        }.map { response -> response.success }

    override suspend fun deleteUser(): Result<Boolean> =
        kotlin.runCatching {
            authDataSource.deleteUser()
        }.map { response -> response.success }

    override suspend fun putUserSetting(isAgreeNotification: Boolean): Result<Boolean> =
        kotlin.runCatching {
            userDataSource.putUserSetting(isAgreeNotification)
        }.map { response -> response.success }

    companion object {
        const val SOCIAL_TYPE_KAKAO = "KAKAO"
    }
}
