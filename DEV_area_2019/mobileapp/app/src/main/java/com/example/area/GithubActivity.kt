package com.example.area

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import io.oauth.OAuth
import io.oauth.OAuthCallback
import io.oauth.OAuthUser
import org.json.JSONObject

/**
 * Class GithubActivity. Activity class, where the user can sign to the Github service and set all Actions Reactions settings.
 */

class GithubActivity : AppCompatActivity() {

    private var oauth : OAuth? = null
    private var users : OAuthUser? = null

    private val provider = "github"

    private var loginBouton : TextView? = null
    private var logoutBouton : TextView? = null

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

    private var progressBar : ProgressBar? = null

    private lateinit var user : User

    private val TAG = "GithubActivity"

    /**
     * Display the activity, allows the user to sign to the Github service and to set Actions Reactions settings.
     */

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_github)

        oauth = OAuth(this)
        oauth?.initialize(getString(R.string.oauth_id))
        users = OAuthUser(oauth)

        loginBouton = findViewById(R.id.login_button_gh)
        logoutBouton = findViewById(R.id.logout_button_gh)

        initialize()
        manageActionView()

        validate!!.setOnClickListener {
            manageActionReactionSettings()
        }

        updateUI(false)

        loginBouton?.setOnClickListener {
            oauth!!.popup(provider, OAuthCallback {

                //////// ACCESS TOKEN ////////
                data -> Log.w(TAG, "Access token " + data.token)
                updateUI(true)
                Toast.makeText(this@GithubActivity, "You are sign in !", Toast.LENGTH_SHORT).show()
                //registerService(data.token)
                ApiRequest().registerService("Github",data.token, progressBar, applicationContext)
            })
        }
        logoutBouton?.setOnClickListener {
            updateUI(false)
            Toast.makeText(this@GithubActivity, "You are sign out !", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Update the login / logout button of the acitivity
     */

    fun updateUI(state : Boolean) {
        if (state) {
            loginBouton?.visibility = View.GONE
            logoutBouton?.visibility = View.VISIBLE
        }
        else {
            logoutBouton?.visibility = View.GONE
            loginBouton?.visibility = View.VISIBLE
        }
    }

    /**
     * This function initialize all the items present in this activity
     */

    private fun initialize() {
        progressBar = findViewById(R.id.GhProgress)
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
        var params = JSONObject()
        val rootObject = JSONObject()
        var sendEmail = JSONObject()

        if (mail_mess?.isChecked!!) {
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