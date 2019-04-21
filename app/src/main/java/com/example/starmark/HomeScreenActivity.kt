package com.example.starmark

import android.app.Activity
import android.app.PendingIntent.getActivity
import android.media.session.PlaybackState
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.widget.LinearLayout
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_home_screen.*
import org.json.JSONArray
import java.io.InputStream
import java.lang.Exception

class HomeScreenActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)

        auth = FirebaseAuth.getInstance()

        val ja = getJSON()!!

        val rv = findViewById<RecyclerView>(R.id.rv_words)

        rv.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)

        val adapter = WordsAdapter(ja)

        rv.adapter = adapter

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menulist, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?) : Boolean = when(item?.itemId){
        R.id.logout -> {
            auth.signOut()
            finish()
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        this.finishAffinity()
        finishAndRemoveTask()
    }

    fun getJSON(): JSONArray?{
        var json:String? = null
        var jsonArr: JSONArray? = null

        try{
            val inputStream: InputStream = assets.open("data.json")
            json = inputStream.bufferedReader().use{it.readText()}

             jsonArr = JSONArray(json)

//            textView_welcome.text = jsonArr.getJSONObject(0).getString("word")

        } catch (e: Exception){

        }

        return jsonArr
    }
}
