package com.example.area

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

/**
 * Class SingUpActivity. Activity class, where the user can create an account in the AREA server.
 */

class SignUpActivity : AppCompatActivity(), View.OnClickListener {

    private var progressBar : ProgressBar? = null

    private var etUserName: EditText? = null
    private var etEmail: EditText? = null
    private var etPassword: EditText? = null
    private var singUp: TextView? = null

    private val TAG = "SignUpActivity"

    private var result : String ? = null

    /**
     * Display the activity, allows the user to create an account in the AREA server.
     */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        initialiser()

        var lin = findViewById(R.id.lin) as TextView

        lin.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            finish()
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent)
            finish()
        }
        singUp = findViewById(R.id.sup)
        singUp?.setOnClickListener(this)
    }

    /**
     * This function initialize all the items present in this activity
     */

    fun initialiser() {

        etUserName = findViewById(R.id.usrusr) as EditText
        etEmail = findViewById(R.id.mail) as EditText
        etPassword = findViewById(R.id.pswrdd) as EditText
        singUp = findViewById(R.id.sup) as TextView
        progressBar = findViewById(R.id.signUpProgress)
    }

    /**
     * Manage the sing up button and send the register user request to the server AREA with all the users infos
     */

    override fun onClick(view: View?) {

        progressBar?.visibility = View.VISIBLE

        if (isInfosValid(etEmail?.text.toString())) {

            val rootObject = JSONObject()
            rootObject.put("name", etUserName?.text)
            rootObject.put("email", etEmail?.text)
            rootObject.put("password", etPassword?.text)

            val que = Volley.newRequestQueue(this)
            val req = JsonObjectRequest(Request.Method.POST, "https://areaepitechrennes.azurewebsites.net/api/users/", rootObject,
                    Response.Listener { response ->
                        progressBar?.visibility = View.GONE
                        Toast.makeText(this@SignUpActivity, "User : " + etUserName?.text.toString() + " has been successfully registered !", Toast.LENGTH_SHORT).show()
                        finish()
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    }, Response.ErrorListener {
                Toast.makeText(this@SignUpActivity, "Error : this user is already register", Toast.LENGTH_SHORT).show()
                progressBar?.visibility = View.GONE
            }
            )
            que.add(req)
        }
    }

    /**
     * Manage the return button to go back to the MainActivity intent
     */

    override fun onBackPressed() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    /**
     * Check if all infos are correct before send the request.
     */

    fun isInfosValid(email: String): Boolean {
        var check : Boolean = false
        if (android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            check = true
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            check = false
            Toast.makeText(this@SignUpActivity, "Email address is incorrect !\nPlease verify it !", Toast.LENGTH_SHORT).show()
        }
        if (etUserName?.text?.toString().isNullOrEmpty()) {
            check = false
            Toast.makeText(this@SignUpActivity, "Error : Empty user name", Toast.LENGTH_SHORT).show()
        }
        if (etPassword?.text?.toString().isNullOrEmpty()) {
            check = false
            Toast.makeText(this@SignUpActivity, "Error : Empty password", Toast.LENGTH_SHORT).show()
        }
        return check
    }
}