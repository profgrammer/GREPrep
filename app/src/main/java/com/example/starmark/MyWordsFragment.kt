package com.example.starmark


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.google.android.gms.tasks.Task
//import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import org.json.JSONArray


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class MyWordsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_my_words, container, false)

        val db = FirebaseFirestore.getInstance()
        val id = arguments?.getString("userId")
        val doc = db.collection("users").document(id!!)
        val json = JSONUtils.getJSON(rootView.context)!!
        val rv = rootView.findViewById(R.id.rv_my_words) as RecyclerView
        rv.layoutManager = LinearLayoutManager(rootView.context, LinearLayout.VERTICAL, false)
//        val x = doc.get().addOnSuccessListener { documentSnapshot ->
//            val data = documentSnapshot.get("words") as MutableList<Int>
//
//        }
//        val y = x.result?.get("words") as MutableList<Int>
//        val ja = JSONArray()
//        for(d in y){
//            ja.put(json[d])
//        }
//        rv.adapter = WordsAdapter(id, ja)
        readData(object : MyCallback{
            override fun onCallback(value: JSONArray) {
                rv.adapter = MyWordsAdapter(id, value)
            }
        }, id, rootView.context)


        return rootView
    }


    fun readData(myCallback: MyCallback, id: String, ctx: Context){
        val db = FirebaseFirestore.getInstance()
        val doc = db.collection("users").document(id)
        val json = JSONUtils.getJSON(ctx)!!
        doc.get().addOnCompleteListener { task ->
            if(task.isSuccessful){
                val ja = JSONArray()
                for(d in task.result?.data?.get("words") as MutableList<Int>){
                    ja.put(json[d])
                }
                myCallback.onCallback(ja)
            }
        }
    }
}
