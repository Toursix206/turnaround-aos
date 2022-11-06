package org.android.turnaround.data.remote.entity.response

import org.android.turnaround.domain.entity.Login
import org.android.turnaround.domain.entity.Token

data class LoginResponse(
    val token: TokenEntity,
    val userId: Int
) {
    fun toLogin(): Login =
        Login(
            token = Token(
                accessToken = this.token.accessToken,
                refreshToken = this.token.refreshToken
            ),
            userId = this.userId
        )
}
