package ua.rash1k.vkgroups

import com.vk.sdk.VKAccessToken
import com.vk.sdk.VKSdk


class CurrentUser {
    companion object {
        fun getAccessToken(): String? {
            return if (VKAccessToken.currentToken() == null) null
            else VKAccessToken.currentToken().accessToken
        }

        fun getUserId(): String? {
            return if (VKAccessToken.currentToken() == null) null
            else VKAccessToken.currentToken().userId
        }


        fun isAuthorized(): Boolean {
            return VKSdk.isLoggedIn()
                    && VKAccessToken.currentToken() != null
                    && !VKAccessToken.currentToken().isExpired
        }
    }
}