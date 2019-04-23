package com.example.starmark

import android.content.Context
import org.json.JSONArray
import java.io.InputStream
import java.lang.Exception

class JSONUtils {

    companion object {
        fun getJSON(ctx: Context): JSONArray?{
            var json:String? = null
            var jsonArr: JSONArray? = null

            try{
                val inputStream: InputStream = ctx.assets.open("data.json")
                json = inputStream.bufferedReader().use{it.readText()}

                jsonArr = JSONArray(json)

//            textView_welcome.text = jsonArr.getJSONObject(0).getString("word")

            } catch (e: Exception){

            }

            return jsonArr
        }
    }


}