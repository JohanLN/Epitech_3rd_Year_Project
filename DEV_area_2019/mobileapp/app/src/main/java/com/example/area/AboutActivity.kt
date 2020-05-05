package com.example.area

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

/**
 * Class AboutActivity. Activity class, where the about.json from AREA server will be display
 */

class AboutActivity : AppCompatActivity() {

    private var progressBar : ProgressBar? = null

    private var textView : TextView ? = null
    private var refreshBtn : TextView ? = null

    private val TAG = "AboutActivity"

    /**
     * Display the activity and shows up the about.json from AREA server
     */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        textView = findViewById(R.id.textAbout)
        refreshBtn = findViewById(R.id.refresh)
        progressBar = findViewById(R.id.FbProgress)

        getAboutJson()

        refreshBtn!!.setOnClickListener {
            getAboutJson()
        }
    }

    /**
     * Request the about.json from AREA server and display it in a TextView from activity_about.xml
     */
    private fun getAboutJson() {

        progressBar?.visibility = View.VISIBLE

        val queue = Volley.newRequestQueue(this)
        val url: String = "https://areaepitechrennes.azurewebsites.net/about.json"

        // Request a string response from the provided URL.
        val stringReq = StringRequest(Request.Method.GET, url,
                Response.Listener<String> { response ->
                    progressBar?.visibility = View.GONE
                    var strResp = response.toString()
                    textView!!.text = strResp
                },
                Response.ErrorListener { error ->
                    textView!!.text = "Error : " + error.networkResponse.statusCode
                    Toast.makeText(this@AboutActivity, "An error occured ! " + error.networkResponse.statusCode, Toast.LENGTH_SHORT).show()
                })
        queue.add(stringReq)
    }
}