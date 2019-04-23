package com.example.starmark

import android.app.Dialog
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.view.menu.ActionMenuItemView
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.database.DatabaseReference
//import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_word_extended.*
import org.json.JSONArray
import org.json.JSONObject
import java.text.FieldPosition

class WordsAdapter(val userId: String ,val wordsList: JSONArray) : RecyclerView.Adapter<WordsAdapter.ViewHolder> () {


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {

//        ref.child(auth.currentUser.toString()).setValue("hello.")
        val v = LayoutInflater.from(p0?.context).inflate(R.layout.layout_words2, p0, false)
        return ViewHolder(v)
    }


    override fun getItemCount(): Int {
        return wordsList.length()
    }



    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0.setIsRecyclable(false)
        val word = wordsList.getJSONObject(p1)

        val w = word.getString("word")
        p0?.textViewWord.text = w

        p0?.word = word.toString()!!

        val db = FirebaseFirestore.getInstance()
//        val doc = db.collection("users").document(userId)
        var d = null
        println("user id: " + userId)
        db.collection("users").document(userId).get()
            .addOnSuccessListener { documentSnapshot ->
                val data = documentSnapshot.get("words") as MutableList<Int>
                for(d in data){
                    if (d == p1){
//                        println("data contains p1")
                        p0?.bookmark.setImageResource(R.drawable.ic_import_contacts_yellow)
                        break
                    }
                }
            }
            .addOnFailureListener {
            }

        p0?.bookmark.setOnClickListener {
            val db = FirebaseFirestore.getInstance()
            val doc = db.collection("users").document(userId)
            doc.get()
                .addOnSuccessListener { documentSnapshot  ->
                    val data = documentSnapshot.get("words") as MutableList<Int>
                    val data1 = mutableListOf<Int>()
                    var found = false
                    for(d in data){
                        if (d != p1){
                            data1.add(d)
                        }
                        else found = true
                    }
                    if(!found) data1.add(p1)
                    val user = HashMap<String, Any>()
                    user["id"] = userId
                    user["email"] = documentSnapshot.get("email") as String
                    user["words"] = data1
                    db.collection("users").document(userId).set(user)

                    notifyDataSetChanged()
                }
                .addOnFailureListener {
                }

        }
    }

    class ViewHolder(itemView: View, var word: String? = null) : RecyclerView.ViewHolder(itemView){
        init {
            itemView.setOnClickListener {
                val word1 = JSONObject(word)
                val dialog = Dialog(itemView.context)
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                dialog.setContentView(R.layout.activity_word_extended)
                dialog.extendedWord.text = word1.getString("word")
                dialog.meaning.text = word1.getString("definition")
                dialog.sentence.text = word1.getString("example")
                dialog.show()
            }
        }

        // different syntax
        val textViewWord = itemView.findViewById(R.id.tv_word) as TextView
        val btn_viewMeaning = itemView.findViewById<Button>(R.id.btn_meaning)
        val bookmark = itemView.findViewById<ImageView>(R.id.img_bookmark)



    }
}
