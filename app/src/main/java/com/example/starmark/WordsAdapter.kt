package com.example.starmark

import android.support.v7.view.menu.ActionMenuItemView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import org.json.JSONArray

class WordsAdapter(val wordsList: JSONArray) : RecyclerView.Adapter<WordsAdapter.ViewHolder> () {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val v = LayoutInflater.from(p0?.context).inflate(R.layout.layout_words2, p0, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return wordsList.length()
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        val word = wordsList.getJSONObject(p1)

        p0?.textViewWord.text = word.getString("word")
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        // different syntax
        val textViewWord = itemView.findViewById(R.id.tv_word) as TextView
        val btn_viewMeaning = itemView.findViewById<Button>(R.id.btn_meaning)

    }
}