package com.example.area

import android.content.Context
import android.content.SharedPreferences

/**
 * Class SharedPrefManager. This class os usefull to save all the users infos even if the application is kill.
 */

class SharedPrefManager private constructor(context: Context) {

    /**
     * Save the id, the user name, the e-mail and the session cookie of the user
     */

    fun userLogin(user: User) {
        val sharedPreferences: SharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString(KEY_ID, user.id)
        editor.putString(KEY_USERNAME, user.username)
        editor.putString(KEY_EMAIL, user.email)
        editor.putString(COOKIE, user.cookie)
        editor.apply()
    }

    val isLoggedIn: Boolean
        get() {
            val sharedPreferences: SharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
            return sharedPreferences.getString(KEY_USERNAME, null) != null
        }

    val user: User
        get() {
            val sharedPreferences: SharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
            return User(
                    sharedPreferences.getString(KEY_ID, null),
                    sharedPreferences.getString(KEY_USERNAME, null),
                    sharedPreferences.getString(KEY_EMAIL, null),
                    sharedPreferences.getString(COOKIE, null)
            )
        }

    /**
     * Clear all the users information.
     */

    fun logout() {
        val sharedPreferences: SharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
        //mCtx.startActivity(Intent(mCtx, LoginActivity::class.java))
    }

    companion object {
        private const val SHARED_PREF_NAME = "simplifiedcodingsharedpref"
        private const val KEY_USERNAME = "keyusername"
        private const val KEY_EMAIL = "keyemail"
        private const val KEY_ID = "keyid"
        private const val COOKIE = "cookie"
        private var mInstance: SharedPrefManager? = null
        private lateinit var mCtx: Context
        @Synchronized
        fun getInstance(context: Context): SharedPrefManager? {
            if (mInstance == null) {
                mInstance = SharedPrefManager(context)
            }
            return mInstance
        }
    }

    init {
        mCtx = context
    }
}