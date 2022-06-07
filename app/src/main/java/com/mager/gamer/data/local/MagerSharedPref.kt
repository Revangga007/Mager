package com.mager.gamer.data.local

import android.app.Application
import com.orhanobut.hawk.Hawk

object MagerSharedPref {
    private const val USER_TOKEN = "userToken"
    private const val REFRESH_TOKEN = "refreshToken"
    private const val USER_ID = "userId"
    private const val USERNAME = "username"
    private const val FULL_NAME = "fullName"
    private const val FOTO_PROFILE = "fotoProfile"
    private const val USER_EMAIL = "userEmail"
    private const val IS_LOGIN = "isLogin"

    fun appInit(application: Application) {
        Hawk.init(application).build()
    }

    var userToken: String? = null
        get() = Hawk.get(USER_TOKEN)
        set(value) {
            Hawk.put(USER_TOKEN, value)
            field = value
        }

    var refreshToken: String? = null
        get() = Hawk.get(REFRESH_TOKEN)
        set(value) {
            Hawk.put(REFRESH_TOKEN, value)
            field = value
        }

    var userId: Int? = null
        get() = Hawk.get(USER_ID)
        set(value) {
            Hawk.put(USER_ID, value)
            field = value
        }

    var userEmail: String? = null
        get() = Hawk.get(USER_EMAIL)
        set(value) {
            Hawk.put(USER_EMAIL, value)
            field = value
        }
    var username: String? = null
        get() = Hawk.get(USERNAME)
        set(value) {
            Hawk.put(USERNAME, value)
            field = value
        }
    var fullName: String? = null
        get() = Hawk.get(FULL_NAME)
        set(value) {
            Hawk.put(FULL_NAME, value)
            field = value
        }

    var fotoProfile: String? = null
        get() = Hawk.get(FOTO_PROFILE)
        set(value) {
            Hawk.put(FOTO_PROFILE, value)
            field = value
        }

    var isLoggedIn: Boolean = false
        get() = Hawk.get(IS_LOGIN, false)
        set(value) {
            Hawk.put(IS_LOGIN, value)
            field = value
        }

    fun clear() {
        Hawk.deleteAll()
    }
}