package com.myapp.grocerli

import android.util.Base64
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object Utilities {
    fun encodeToBase64(str: String): String {
        return Base64.encodeToString(str.toByteArray(charset("UTF-8")),
                Base64.DEFAULT)
    }
    fun decodeBase64(encoded: String): String {
        return Base64.decode(encoded, Base64.DEFAULT).toString(charset("UTF-8"))
    }

    /*to convert serializable to map of String*/
//convert an object of type I to type O
    inline fun <I, reified O> I.convert(): O {
        val gson = Gson()
        val json = if (this is String) this else gson.toJson(this)
        return gson.fromJson(json, object : TypeToken<O>() {}.type)
    }
}