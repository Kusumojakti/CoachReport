package com.example.coachreport.utils

import android.content.Context
import android.content.SharedPreferences
import com.example.coachreport.R

object SessionManager {
    private const val USER_TOKEN = "token"

    fun saveAuthToken(context: Context, token: String) {
        saveString(context, USER_TOKEN, token)
    }

    fun saveUserdata(context: Context, name: String, email: String, id: String, noHp: String) {
        saveString(context, "id", id)
        saveString(context, "name", name)
        saveString(context, "noHp", noHp)
        saveString(context, "email", email)
    }

    fun getToken(context: Context): String? {
        return getString(context, USER_TOKEN)
    }

    fun getName(context: Context): String? {
        return getString(context, "name")
    }

    fun getEmail(context: Context): String? {
        return getString(context, "email")
    }

    fun getUserid(context: Context): String? {
        return getString(context, "id")
    }

    fun getnohp(context: Context): String? {
        return getString(context, "noHp")
    }

    private fun saveString(context: Context, key: String, value: String) {
        val pref: SharedPreferences = context.getSharedPreferences(context.getString(R.string.app_name),
            Context.MODE_PRIVATE)
        val editor = pref.edit()
        editor.putString(key, value)
        editor.apply()
    }


    private fun getString(context: Context, key: String) : String? {
        val pref: SharedPreferences = context.getSharedPreferences(context.getString(R.string.app_name),
            Context.MODE_PRIVATE)
        return pref.getString(key, null)
    }

    fun clearData(context: Context) {
        val editor = context.getSharedPreferences(context.getString(R.string.app_name),
            Context.MODE_PRIVATE).edit()
        editor.remove(USER_TOKEN)
        editor.remove("id")
        editor.remove("name")
        editor.remove("email")
        editor.apply()
    }
}