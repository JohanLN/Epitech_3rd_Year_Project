package com.example.area

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.facebook.*
import com.facebook.login.LoginResult
import kotlinx.android.synthetic.main.activity_facebook.*
import org.json.JSONObject
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

/**
 * Class FacebookActivity. Activity class, where the user can sign to the Facebook service and set all Actions Reactions settings.
 */

class FacebookActivity : AppCompatActivity() {

    val callbackManager = CallbackManager.Factory.create()

    private var validate : TextView ? = null

    private var Got_Message : TextView ? = null
    private var Got_Like : TextView ? = null
    private var Got_Com : TextView ? = null
    private var Got_Pub : TextView ? = null
    private var Got_Gp : TextView ? = null

    private var got_mess: LinearLayout ? = null
    private var got_like: LinearLayout ? = null
    private var got_com: LinearLayout ? = null
    private var got_pub: LinearLayout ? = null
    private var got_gp: LinearLayout ? = null

    private var mail_mess : CheckBox ? = null
    private var mail_like : CheckBox ? = null
    private var mail_com : CheckBox ? = null
    private var mail_pub : CheckBox ? = null
    private var mail_gp : CheckBox ? = null

    private val TAG = "FacebookActivity"

    private var progressBar : ProgressBar? = null

    private lateinit var user : User

    /**
     * Display the activity, allows the user to sign to the Facebook service and to set Actions Reactions settings.
     */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_facebook)

        initialize()

        facebookLogin()

        manageActionView()

        validate?.setOnClickListener {
            manageActionReactionSettings()
        }

    }

    /**
     * Perform the login to the service thanks to the Facebook SDK
     */

    fun facebookLogin() {
        login_button.setReadPermissions("public_profile")
        generateHashKey()

        if (AccessToken.getCurrentAccessToken() == null) {
            Log.d("FacebookActivity", "No Access Token need to create one")
        } else {

            Log.d("FacebookActivity", "You already have an access token")
            Toast.makeText(this@FacebookActivity, "You are sign as : " + Profile.getCurrentProfile().name, Toast.LENGTH_SHORT).show()
            if (AccessToken.getCurrentAccessToken().isExpired) {
                Log.d("FacebookActivity", "Your access token is expires")
            }

            ////////////  ACCESS TOKEN //////////////////////////
            val accessToken = AccessToken.getCurrentAccessToken()
            ApiRequest().registerService("Facebook", accessToken.token, progressBar, applicationContext)
            Log.w(TAG, accessToken.token)
        }

        login_button.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onError(error: FacebookException?) {
                Log.d("FacebookActivity", "Auth Error = " + error?.message)
            }

            override fun onSuccess(result: LoginResult?) {

                if (AccessToken.getCurrentAccessToken() != null) {
                    val profile = Profile.getCurrentProfile()
                    if (profile != null) {
                        Log.d("FacebookActivity", "Auth Success = " + result?.accessToken?.token)
                        //registerService(result?.accessToken!!.token)
                        ApiRequest().registerService("Facebook", result?.accessToken!!.token, progressBar, this@FacebookActivity)
                        Log.w(TAG, result?.accessToken!!.token)
                    }

                }
            }

            override fun onCancel() {
            }
        })
    }

    /**
     * generate an hash key and send it to the Facebook service
     */

    fun generateHashKey() {
        try {
            val info = packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES)
            for (signature in info.signatures) {
                val md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT))
            }
        } catch (e: PackageManager.NameNotFoundException) {
            Log.d("FacebookActivity", "No Package for this name = " + e.printStackTrace())
        } catch (e: NoSuchAlgorithmException) {
            Log.d("FacebookActivity", "Algo Exception = " + e.printStackTrace())
        }
    }

    /**
     * Get result from the login request
     */

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * This function initialize all the items present in this activity
     */

    private fun initialize() {
        progressBar = findViewById(R.id.FbProgress)
        user = SharedPrefManager.getInstance(this)!!.user

        ApiRequest().getUser(progressBar, this, user)

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

        if (mail_mess?.isChecked!!) {
            var params = JSONObject()
            val rootObject = JSONObject()
            var sendEmail = JSONObject()

            rootObject.put("name", "GotMessage $TAG")
            rootObject.put("description", "The user wants to have an e-mail when he receive a message")

            sendEmail.put("name", "WillSendMessage $TAG")
            sendEmail.put("description", "The user wants to have an e-mail when he receive a message")
            sendEmail.put("actions", actions)


            params.put("target", user.username)
            params.put("content", "You recieved a $TAG message")

            sendEmail.put("params", params)

            rootObject.put("reaction", sendEmail)
            ApiRequest().registerActionsReactions(progressBar, this, rootObject)
        }

        if (mail_like?.isChecked!!) {
            var params = JSONObject()
            val rootObject = JSONObject()
            var sendEmail = JSONObject()

            rootObject.put("name", "GotLike $TAG")
            rootObject.put("description", "The user wants to have an e-mail when he receive a like")

            sendEmail.put("name", "WillSendMessage $TAG")
            sendEmail.put("description", "The user wants to have an e-mail when he receive a like")
            sendEmail.put("actions", actions)


            params.put("target", user.username)
            params.put("content", "You recieved a $TAG like")

            sendEmail.put("params", params)

            rootObject.put("reaction", sendEmail)
            ApiRequest().registerActionsReactions(progressBar, this, rootObject)
        }

        if (mail_com?.isChecked!!) {
            var params = JSONObject()
            val rootObject = JSONObject()
            var sendEmail = JSONObject()

            rootObject.put("name", "GotComment $TAG")
            rootObject.put("description", "The user wants to have an e-mail when he receive a comment on a post")

            sendEmail.put("name", "WillSendMessage $TAG")
            sendEmail.put("description", "The user wants to have an e-mail when he receive a comment on a post")
            sendEmail.put("actions", actions)


            params.put("target", user.username)
            params.put("content", "You recieved a $TAG comment")

            sendEmail.put("params", params)

            rootObject.put("reaction", sendEmail)
            ApiRequest().registerActionsReactions(progressBar, this, rootObject)
        }

        if (mail_pub?.isChecked!!) {
            var params = JSONObject()
            val rootObject = JSONObject()
            var sendEmail = JSONObject()

            rootObject.put("name", "GotNewPublish $TAG")
            rootObject.put("description", "The user wants to have an e-mail when he receive a new publication")

            sendEmail.put("name", "WillSendMessage $TAG")
            sendEmail.put("description", "The user wants to have an e-mail when he receive a new publication")
            sendEmail.put("actions", actions)


            params.put("target", user.username)
            params.put("content", "You recieved a $TAG publication")

            sendEmail.put("params", params)

            rootObject.put("reaction", sendEmail)
            ApiRequest().registerActionsReactions(progressBar, this, rootObject)
        }

        if (mail_gp?.isChecked!!) {
            var params = JSONObject()
            val rootObject = JSONObject()
            var sendEmail = JSONObject()

            rootObject.put("name", "GotNewGroupPost $TAG")
            rootObject.put("description", "The user wants to have an e-mail when he receive a new group post")

            sendEmail.put("name", "WillSendMessage $TAG")
            sendEmail.put("description", "The user wants to have an e-mail when he receive a new group post")
            sendEmail.put("actions", actions)


            params.put("target", user.username)
            params.put("content", "You recieved a $TAG group post")

            sendEmail.put("params", params)

            rootObject.put("reaction", sendEmail)
            ApiRequest().registerActionsReactions(progressBar, this, rootObject)
        }

    }
}