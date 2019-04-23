package com.example.starmark

import android.app.Dialog
import android.support.v7.view.menu.ActionMenuItemView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_word_extended.*
import org.json.JSONArray
import org.json.JSONObject

class MyWordsAdapter(val userId: String ,val wordsList: JSONArray) : RecyclerView.Adapter<MyWordsAdapter.ViewHolder> (){
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val v = LayoutInflater.from(p0?.context).inflate(R.layout.layout_mywords, p0, false)
        return MyWordsAdapter.ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return wordsList.length()
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        val wordObj = wordsList.getJSONObject(p1)
        p0.word.text = wordObj.getString("word")
//        p0.example.text = wordObj.getString("example")
//        p0.meaning.text = wordObj.getString("definition")
        p0.wordObj = wordObj.toString()
    }


    class ViewHolder (itemView: View,var wordObj: String? = null) : RecyclerView.ViewHolder(itemView){

        init{
            itemView.setOnClickListener {
                val word1 = JSONObject(wordObj)
                val dialog = Dialog(itemView.context)
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                dialog.setContentView(R.layout.activity_word_extended)
                dialog.extendedWord.text = word1.getString("word")
                dialog.meaning.text = word1.getString("definition")
                dialog.sentence.text = word1.getString("example")
                dialog.show()
            }
        }

        val word = itemView.findViewById<TextView>(R.id.tv_word)
//        val meaning = itemView.findViewById<TextView>(R.id.meaning)
//        val example = itemView.findViewById<TextView>(R.id.sentence)
    }

}