package org.android.turnaround.data.remote.repository

import org.android.turnaround.domain.entity.SignUp
import org.android.turnaround.domain.entity.Token

interface AuthRepository {
    fun checkIsUser(): Boolean
    fun initKakaoToken(kakaoToken: String)
    fun initTurnAroundToken(token:Token)
    suspend fun postNicknameValid(nickname: String): Result<Boolean>
    suspend fun postSignUp(nickname: String, profileType: String): Result<SignUp>
}
