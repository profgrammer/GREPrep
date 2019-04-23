package com.example.starmark

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_word_extended.*
import org.json.JSONObject

class WordExtendedActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_word_extended)

        val word = intent.getStringExtra("data")
        val wordObj = JSONObject(word)
        extendedWord.text = wordObj.getString("word")
        meaning.text = wordObj.getString("definition")
        sentence.text = wordObj.getString("example")
    }
}
