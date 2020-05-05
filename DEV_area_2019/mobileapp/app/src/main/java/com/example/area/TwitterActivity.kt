package com.example.area

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity

import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.CookieManager
import android.webkit.CookieSyncManager
import android.widget.*
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.twitter.sdk.android.core.*
import com.twitter.sdk.android.core.identity.TwitterAuthClient
import org.json.JSONObject

/**
 * Class TwitterActivity. Activity class, where the user can sign to the Twitter service and set all Actions Reactions settings.
 */

class TwitterActivity : AppCompatActivity(), View.OnClickListener {

    private val TAG = "TwitterActivity"
    val CONSUMER_KEY = "9dGxBsnkJTiX8oykKstwEVSiM"
    val CONSUMER_SECRET = "1bM0XjMELzOAlOlD64YnqmQ3cMljh0LDtiYWHx9ItZoEDxWDBg"

    internal var mTwitterAuthClient: TwitterAuthClient? = null

    var loginButton: TextView ? = null
    var logoutButton: TextView ? = null

    internal var session: TwitterSession? = null

    private var progressBar : ProgressBar? = null

    private var validate : TextView ? = null

    private var Got_Message : TextView ? = null
    private var Got_Like : TextView ? = null
    private var Got_Com : TextView ? = null
    private var Got_Pub : TextView ? = null
    private var Got_Gp : TextView ? = null

    private var got_mess: LinearLayout? = null
    private var got_like: LinearLayout? = null
    private var got_com: LinearLayout? = null
    private var got_pub: LinearLayout? = null
    private var got_gp: LinearLayout? = null

    private var mail_mess : CheckBox? = null
    private var mail_like : CheckBox? = null
    private var mail_com : CheckBox? = null
    private var mail_pub : CheckBox? = null
    private var mail_gp : CheckBox? = null

    /**
     * Display the activity, allows the user to sign to the Slack service and to set Actions Reactions settings.
     */

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        val config = TwitterConfig.Builder(this)
                .logger(DefaultLogger(Log.DEBUG))//enable logging when app is in debug mode
                .twitterAuthConfig(
                        TwitterAuthConfig(
                                CONSUMER_KEY,
                                CONSUMER_SECRET
                        )
                )
                .debug(true)
                .build()

        Twitter.initialize(config)

        setContentView(R.layout.activity_twitter)

        initialize()
        manageActionView()

        validate!!.setOnClickListener {
            manageActionReactionSettings()
        }

        loginButton = findViewById(R.id.login_button_tw)
        loginButton!!.setOnClickListener(this)

        logoutButton = findViewById(R.id.logout_button_tw)
        logoutButton!!.setOnClickListener(this)

        checkTwitterSession()

        mTwitterAuthClient = TwitterAuthClient()
    }

    /**
     * Display the activity, allows the user to sign to the Slack service and to set Actions Reactions settings.
     */

    private fun initialize() {
        progressBar = findViewById(R.id.TwProgress)

        validate = findViewById(R.id.validate)

        Got_Message = findViewById(R.id.GotMessage)
        Got_Like = findViewById(R.id.GotALike)
        Got_Com = findViewById(R.id.GotACom)
        Got_Pub = findViewById(R.id.GotAPub)
        Got_Gp = findViewById(R.id.GotAGP)

        got_mess = findViewById(R.id.got_message)
        got_like = findViewById(R.id.got_a_like)
        got_com = findViewById(R.id.got_a_com)
        got_pub = findViewById(R.id.got_a_pub)
        got_gp = findViewById(R.id.got_a_gp)

        mail_mess = findViewById(R.id.SendMail_mess)
        mail_like = findViewById(R.id.SendMail_like)
        mail_com = findViewById(R.id.SendMail_com)
        mail_pub = findViewById(R.id.SendMail_pub)
        mail_gp = findViewById(R.id.SendMail_gp)
    }

    /**
     * This function permit to update the UI in terms of the selected action.
     */

    private fun manageActionView() {
        Got_Message?.setOnClickListener {
            got_mess?.visibility = View.VISIBLE
            got_like?.visibility = View.GONE
            got_com?.visibility = View.GONE
            got_pub?.visibility = View.GONE
            got_gp?.visibility = View.GONE
        }

        Got_Like?.setOnClickListener {
            got_like?.visibility = View.VISIBLE
            got_mess?.visibility = View.GONE
            got_com?.visibility = View.GONE
            got_pub?.visibility = View.GONE
            got_gp?.visibility = View.GONE
        }

        Got_Com?.setOnClickListener {
            got_like?.visibility = View.GONE
            got_mess?.visibility = View.GONE
            got_com?.visibility = View.VISIBLE
            got_pub?.visibility = View.GONE
            got_gp?.visibility = View.GONE
        }

        Got_Pub?.setOnClickListener {
            got_like?.visibility = View.GONE
            got_mess?.visibility = View.GONE
            got_com?.visibility = View.GONE
            got_pub?.visibility = View.VISIBLE
            got_gp?.visibility = View.GONE
        }

        Got_Gp?.setOnClickListener {
            got_like?.visibility = View.GONE
            got_mess?.visibility = View.GONE
            got_com?.visibility = View.GONE
            got_pub?.visibility = View.GONE
            got_gp?.visibility = View.VISIBLE
        }

    }

    /**
     * This function makes the call registerActionsReactions from ApiRequest() in terms of the checkBoxes inside the Action slide
     */

    private fun manageActionReactionSettings() {
        var actions = null
        var params = null
        val rootObject = JSONObject()
        var sendEmail = JSONObject()

        sendEmail.put("name", "WillSendMessage")
        sendEmail.put("description", "")
        sendEmail.put("actions", actions)
        sendEmail.put("params", params)


        if (mail_mess?.isChecked!!) {
            rootObject.put("name", "GotMessage")
            rootObject.put("description", "The user wants to have an e-mail when he receive a message")
            rootObject.put("reaction", sendEmail)
        }

        if (mail_like?.isChecked!!) {
            rootObject.put("name", "GotLike")
            rootObject.put("description", "The user wants to have an e-mail when he receive a like")
            rootObject.put("reaction", sendEmail)
        }

        if (mail_com?.isChecked!!) {
            rootObject.put("name", "GoComment")
            rootObject.put("description", "The user wants to have an e-mail when he receive a comment")
            rootObject.put("reaction", sendEmail)
        }

        if (mail_pub?.isChecked!!) {
            rootObject.put("name", "GotNewPublish")
            rootObject.put("description", "The user wants to have an e-mail when he receive a new publication notification")
            rootObject.put("reaction", sendEmail)
        }

        if (mail_gp?.isChecked!!) {
            rootObject.put("name", "GotNewGroupPost")
            rootObject.put("description", "The user wants to have an e-mail when he receive a new post from a groupe")
            rootObject.put("reaction", sendEmail)
        }

        progressBar?.visibility = View.VISIBLE

        val que = Volley.newRequestQueue(this)
        val req = JsonObjectRequest(Request.Method.POST, "https://areaepitechrennes.azurewebsites.net/api/RegisteredActions/", rootObject,
                Response.Listener { response ->
                    progressBar?.visibility = View.GONE
                    Toast.makeText(this@TwitterActivity, "Settings has been set !", Toast.LENGTH_SHORT).show()
                    Log.w(TAG, "Response = $response")
                }, Response.ErrorListener { error ->
            progressBar?.visibility = View.GONE
            Log.w(TAG, "An error occured ! : " + error.networkResponse.statusCode)
            Toast.makeText(this@TwitterActivity, "An error occured ! " + error.networkResponse.statusCode, Toast.LENGTH_SHORT).show()
        }
        )
        que.add(req)

    }

    /**
     * Check if a user is already connected.
     */

    private fun checkTwitterSession() {
        if (TwitterCore.getInstance()?.sessionManager?.activeSession?.userName != null) {
            Toast.makeText(this@TwitterActivity, "You are sign as : " + TwitterCore.getInstance()?.sessionManager?.activeSession?.userName, Toast.LENGTH_SHORT).show()
            updateUi(true)
            //registerService(TwitterCore.getInstance()?.sessionManager?.activeSession?.authToken?.token)
            var token = ""
            TwitterCore.getInstance()?.sessionManager?.activeSession?.authToken?.token?.let { token = it }
            ApiRequest().registerService("Twitter",token, progressBar, applicationContext)
        }
        else
            updateUi(false)
    }

    /**
     * Get users infos of the active sessiosn.
     */

    private fun getTwitterSession(): TwitterSession? {
        return TwitterCore.getInstance()!!.sessionManager!!.activeSession
    }

    /**
     * Perform the user login to Twitter service
     */

    fun twitterLogin() {
        Log.d(TAG, "Passed on TwitterLogin")
        if (getTwitterSession() == null) {
            mTwitterAuthClient!!.authorize(this, object : Callback<TwitterSession>() {
                override fun success(twitterSessionResult: Result<TwitterSession>) {
                    Toast.makeText(this@TwitterActivity, "Login Success", Toast.LENGTH_SHORT).show()
                    Log.d(TAG, "Success = " + twitterSessionResult.data.authToken.token)
                    val twitterSession = twitterSessionResult.data
                    fetchTwitterEmail(twitterSession)

                    ////////// ACCESS TOKEN ////////////////////////
                    var accessToken = twitterSession.authToken.token
                    //registerService(accessToken)
                    ApiRequest().registerService("Twitter",accessToken, progressBar, applicationContext)

                }

                override fun failure(e: TwitterException) {
                    Toast.makeText(this@TwitterActivity, "Failure : " + e.message, Toast.LENGTH_SHORT).show()
                    Log.d(TAG, "Failed = " + e.message)
                }
            })
        } else {
            fetchTwitterEmail(getTwitterSession())
        }
    }

    /**
     * Send users infos to Twitter service
     */

    fun fetchTwitterEmail(twitterSession: TwitterSession?) {
        mTwitterAuthClient?.requestEmail(twitterSession, object : Callback<String>() {
            override fun success(result: Result<String>) {

                Log.d(TAG, "twitterLogin:userId" + twitterSession!!.userId)
                Log.d(TAG, "twitterLogin:userName" + twitterSession.userName)
                Log.d(TAG, "twitterLogin: result.data" + result.data)
                updateUi(true)

            }

            override fun failure(exception: TwitterException) {
                Toast.makeText(this@TwitterActivity, "Failed to authenticate. Please try again.", Toast.LENGTH_SHORT)
                        .show()
                updateUi(false)
            }
        })
    }

    /**
     * Get result from the login request
     */

    @SuppressLint("MissingSuperCall")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (mTwitterAuthClient != null)
            mTwitterAuthClient!!.onActivityResult(requestCode, resultCode, data)
    }

    /**
     * Manage the login button and send the login request to Twitter service
     */

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.login_button_tw -> {
                if (checkInternetConnection(this@TwitterActivity))
                    twitterLogin()
                else
                    Toast.makeText(this@TwitterActivity, "Check your internet connection !", Toast.LENGTH_SHORT).show()
            }

            R.id.logout_button_tw -> {
                if (checkInternetConnection(this@TwitterActivity)){
                    Toast.makeText(this@TwitterActivity, "Logout Successfull.", Toast.LENGTH_SHORT)
                            .show()
                    CookieSyncManager.createInstance(this);
                    val cookieManager = CookieManager.getInstance();
                    cookieManager.removeSessionCookie();
                    TwitterCore.getInstance().getSessionManager().clearActiveSession()
                    updateUi(false)
                }
                else
                    Toast.makeText(this@TwitterActivity, "Check your internet connection !", Toast.LENGTH_SHORT).show()
            }
        }

    }

    /**
     * Check if internet is available.
     */

    fun checkInternetConnection(context: Context): Boolean {

        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return true
    }

    /**
     * Update the login / logout button of the acitivity
     */

    fun updateUi(state : Boolean) {
        if (state) {
            loginButton?.visibility = View.GONE
            logoutButton?.visibility = View.VISIBLE
        }
        else if (!state) {
            loginButton?.visibility = View.VISIBLE
            logoutButton?.visibility = View.GONE
        }
    }
}