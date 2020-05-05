package com.example.area

import android.content.Intent
import android.os.Bundle
import android.text.Layout
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

/**
 * Class HomeActivity. Activity class, where the user can select the desired service and also get the about.json from AREA server.
 */

class HomeActivity : AppCompatActivity() {

    private var fbButton: ImageView ? = null
    private var ttButton: ImageView ? = null
    private var linButton: ImageView ? = null
    private var instButton: ImageView ? = null
    private var gooButton: ImageView ? = null
    private var ytButton: ImageView ? = null
    private var gauButton: ImageView ? = null
    private var aboButton: ImageView ? = null

    /**
     * Display the activity, shows up all the available services.
     */

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        fbButton = findViewById(R.id.fb) as ImageView
        ttButton = findViewById(R.id.tt) as ImageView
        linButton = findViewById(R.id.lin) as ImageView
        instButton = findViewById(R.id.ins) as ImageView
        gooButton = findViewById(R.id.goo) as ImageView
        ytButton = findViewById(R.id.yt) as ImageView
        gauButton = findViewById(R.id.gau) as ImageView
        aboButton = findViewById(R.id.abo) as ImageView

        fbButton!!.setOnClickListener {
            val intent = Intent(this, FacebookActivity::class.java)
            startActivity(intent)        }
        ttButton!!.setOnClickListener {
            val intent = Intent(this, TwitterActivity::class.java)
            startActivity(intent)        }
        instButton!!.setOnClickListener {
            val intent = Intent(this, DiscordActivity::class.java)
            startActivity(intent)        }
        linButton!!.setOnClickListener {
            val intent = Intent(this, LinkedinActivity::class.java)
            startActivity(intent)        }
        gooButton!!.setOnClickListener {
            val intent = Intent(this, ImgurActivity::class.java)
            startActivity(intent)        }
        ytButton!!.setOnClickListener {
            val intent = Intent(this, SlackActivity::class.java)
            startActivity(intent)        }
        gauButton!!.setOnClickListener {
            val intent = Intent(this, GithubActivity::class.java)
            startActivity(intent)        }
        aboButton!!.setOnClickListener {
            val intent = Intent(this, AboutActivity::class.java)
            startActivity(intent)        }
    }

    /**
     * Manage the return button from smartphone to present the disconnection interface.
     */

    override fun onBackPressed() {
        var home_layout = findViewById<View>(R.id.Home)
        var disconnect_layout = findViewById<View>(R.id.disconnection)
        var yes = findViewById<TextView>(R.id.yes)
        var no = findViewById<TextView>(R.id.no)

        home_layout.visibility = View.GONE
        disconnect_layout.visibility = View.VISIBLE

        yes.setOnClickListener {
            Toast.makeText(this@HomeActivity, "You are disconnected !", Toast.LENGTH_SHORT).show()
            SharedPrefManager.getInstance(applicationContext)!!.logout()
            super.onBackPressed()
        }
        no.setOnClickListener {
            home_layout.visibility = View.VISIBLE
            disconnect_layout.visibility = View.GONE
        }
    }
}