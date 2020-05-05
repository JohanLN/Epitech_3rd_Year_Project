package com.example.area

import org.json.JSONObject

/**
 * Class User. Get all the user information to save them in the SharedPrefManager() class.
 */

class User(val id: String?, val username: String?, val email: String?, val cookie : String?) {

    private lateinit var areaSettings: JSONObject

    fun setAreaSettings(areaSetting : JSONObject) {
        this.areaSettings = areaSetting
    }

    fun getAreaSettings() : JSONObject {
        return this.areaSettings
    }
}