package com.example.area

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.extensions.jsonBody
import com.github.kittinunf.fuel.json.responseJson
import org.json.JSONArray
import org.json.JSONObject

/**
 * Class MainActivity. Activity class, where the user can sign to the AREA server.
 */

class MainActivity : AppCompatActivity() {

    private var progressBar: ProgressBar? = null

    private val TAG = "MainActivity"

    /**
     * Display the activity, allows the user to sign to the AREA server.
     */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val userName = findViewById(R.id.userName) as EditText

        val password = findViewById(R.id.pswrdd) as EditText

        progressBar = findViewById(R.id.signInProgress)

        if (SharedPrefManager.getInstance(applicationContext)!!.isLoggedIn) {
            val user = SharedPrefManager.getInstance(applicationContext)!!.user
            Toast.makeText(this@MainActivity, user.username + " is sign in", Toast.LENGTH_SHORT).show()
            var intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

        var loginButton = findViewById(R.id.lin) as TextView
        loginButton.setOnClickListener { view ->
            if (checkInfos(userName.text.toString(), password.text.toString())) {
                ApiRequest().signIn(userName.text.toString(), password.text.toString(), this, progressBar)
            }
        }

        var sup = findViewById(R.id.sup) as TextView

        sup.setOnClickListener {
            finish()
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

    }

    /**
     * Will check if all the infos are correct to perform the sign in request.
     */

    fun checkInfos(userName: String, password: String) : Boolean {
        var check = true

        if (userName.isNullOrEmpty()) {
            check = false
            Toast.makeText(this@MainActivity, "Error : please enter your user name !", Toast.LENGTH_SHORT).show()
        }
        if (password.isNullOrEmpty()) {
            check = false
            Toast.makeText(this@MainActivity, "Error : please enter your password !", Toast.LENGTH_SHORT).show()
        }
        return check
    }

}
