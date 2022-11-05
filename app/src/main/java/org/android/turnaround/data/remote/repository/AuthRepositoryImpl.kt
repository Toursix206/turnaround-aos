package org.android.turnaround.data.remote.repository

import org.android.turnaround.data.local.datasource.LocalAuthPrefDataSource
import org.android.turnaround.data.remote.datasource.AuthDataSource
import org.android.turnaround.domain.entity.SignUp
import timber.log.Timber
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val localAuthPrefDataSource: LocalAuthPrefDataSource,
    private val authDataSource: AuthDataSource
) : AuthRepository {
    override fun initKakaoToken(kakaoToken: String) {
        localAuthPrefDataSource.socialType = SOCIAL_TYPE_KAKAO
        localAuthPrefDataSource.kakaoToken = kakaoToken
        // TODO 추후 fcmToken 불러와야함.
        localAuthPrefDataSource.fcmToken = "dummyFCMToken"
    }

    override suspend fun postNicknameValid(nickname: String): Result<Boolean> =
        kotlin.runCatching { authDataSource.postNicknameValid(nickname) }
            .map { response -> response.success }

    override suspend fun postSignUp(
        nickname: String,
        profileType: String
    ): Result<SignUp> =
        kotlin.runCatching {
            Timber.d("$nickname, $profileType, ${localAuthPrefDataSource.fcmToken}, ${localAuthPrefDataSource.socialType}, ${localAuthPrefDataSource.kakaoToken} ")
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

    companion object {
        const val SOCIAL_TYPE_KAKAO = "KAKAO"
    }
}
