package org.android.turnaround.domain.repository

import org.android.turnaround.domain.entity.Login
import org.android.turnaround.domain.entity.SignUp
import org.android.turnaround.domain.entity.Token

interface AuthRepository {
    fun checkIsUser(): Boolean
    fun initFcmToken(isInitFcmToken: (Boolean) -> Unit)
    fun initKakaoToken(kakaoToken: String, isInitKakaoToken: (Boolean) -> Unit)
    fun initTurnAroundToken(token: Token)
    fun clearLocalPref()
    suspend fun postNicknameValid(nickname: String): Result<Boolean>
    suspend fun postSignUp(nickname: String, profileType: String): Result<SignUp>
    suspend fun postLogin(): Result<Login>
    suspend fun postLogout(): Result<Boolean>
    suspend fun deleteUser(): Result<Boolean>
}
