package com.example.area

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.ProgressBar
import androidx.core.content.ContextCompat.startActivity
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.Headers
import com.github.kittinunf.fuel.core.extensions.jsonBody
import com.github.kittinunf.fuel.json.responseJson
import org.json.JSONObject

/**
 * Class API Request.
 * Makes differents requests to the AREA server like Log in / RegisterServices / RegisterActionsReactions.
 */

class ApiRequest {

    private val TAG = "ApiRequest"

    /**
     * Makes a request to AREA server to register a service from his context. For example Facebook as service, and send the user access token.
     */

    fun registerService(service: String, accessToken: String, progressBar: ProgressBar?, context: Context) {

        val user = SharedPrefManager.getInstance(context)!!.user
        var cookie = ""
        user.cookie?.let { cookie = it }

        val rootObject = JSONObject()
        rootObject.put("name", service)
        rootObject.put("accessToken", accessToken)

         Fuel.post("https://areaepitechrennes.azurewebsites.net/api/RegisteredServices/")
                .jsonBody(rootObject.toString())
                .header(Headers.COOKIE to cookie)
                .responseJson { request, respons, result ->
                    result.fold(
                            success = {
                                json ->
                                Log.w(TAG, "Services Ok = " + json.array().toString())
                            },
                            failure = {
                                error ->
                                Log.w(TAG, "Services Error = " + error.toString())
                            }
                    )
                }
    }

    /**
     * Get user infos from AREA server, to know what services are already registered and also the actions reactions settings.
     */

    fun getUser(progressBar: ProgressBar?, context: Context, user : User) {

        val user = SharedPrefManager.getInstance(context)!!.user
        var cookie = ""
        user.cookie?.let { cookie = it }


        Fuel.get("https://areaepitechrennes.azurewebsites.net/api/users/" + user.id)
                .header(Headers.COOKIE to cookie)
                .responseJson { request, respons, result ->
                    result.fold(
                            success = {
                                json ->
                                Log.w(TAG, "Ok = " + json.obj().toString())
                                user.setAreaSettings(json.obj())
                                Log.w(TAG, "User AREA = " + user.getAreaSettings())

                            },
                            failure = {
                                error ->
                                Log.w(TAG, "Error = " + error.toString())
                            })
                }
    }

    /**
     * Send to the AREA server all the settings that the user set for actions and reactions from his context in a JSONObject.
     */

    fun registerActionsReactions(progressBar: ProgressBar?, context: Context, actionsReactions : JSONObject) {

        val user = SharedPrefManager.getInstance(context)!!.user
        var cookie = ""
        user.cookie?.let { cookie = it }


        Fuel.post("https://areaepitechrennes.azurewebsites.net/api/Actions/")
                .jsonBody(actionsReactions.toString())
                .header(Headers.COOKIE to cookie)
                .responseJson { request, respons, result ->
                    result.fold(
                            success = {
                                json ->
                                Log.w(TAG, "Registered Actions Ok = " + json.array().toString())

                            },
                            failure = {
                                error ->
                                Log.w(TAG, "Registered Actions Error = " + error.toString())
                            })
                }

    }

    /**
     * Request to the AREA server a connection for user !
    */

    fun signIn(userName: String, password: String, context : Context, progressBar: ProgressBar?) {

        val rootObject = JSONObject()
        rootObject.put("name", userName)
        rootObject.put("password", password)

        Fuel.post("https://areaepitechrennes.azurewebsites.net/api/users/login")
                .jsonBody(rootObject.toString())
                .responseJson { request, response, result ->
                    result.fold(
                            success = {

                                json ->
                                Log.w(TAG, "Success = "  + " Email, " +json.obj().get("registeredActions"))
                                var cookie = response.headers.get("Set-Cookie")
                                var resCookie = cookie.first() + " " + cookie.last()
                                var user = User(json.obj().get("id").toString(), json.obj().get("name").toString(), json.obj().get("email").toString(), resCookie)

                                Log.w(TAG, "Id : " + user.id + ", Username : " + user.username + ", Email : " + user.email + ", Cookie " + user.cookie)
                                SharedPrefManager.getInstance(context)!!.userLogin(user)

                                var intent = Intent(context, HomeActivity::class.java)
                                startActivity(context, intent, null)

                            },
                            failure = {

                                error ->
                                Log.w(TAG, error.toString())
                            })
                }
    }
}