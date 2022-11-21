package org.android.turnaround.data.remote.entity.response

import org.android.turnaround.domain.entity.RefreshToken
import org.android.turnaround.domain.entity.Token

data class RefreshTokenResponse(
    val token: TokenEntity
) {
    fun toRefreshToken(): RefreshToken =
        RefreshToken(
            token = Token(
                accessToken = this.token.accessToken,
                refreshToken = this.token.refreshToken
            )
        )
}
