package com.midwestpilotcars.helpers

import javax.inject.Singleton
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.google.gson.Gson
import com.midwestpilotcars.constants.SharedPrefConstants
import com.midwestpilotcars.models.allDefaultModels.AllDefaultDataModel
import com.midwestpilotcars.models.loginModels.LoginResponseModel


@Singleton
class SharedPreferenceHelper private constructor() {
    companion object {
        private var sharedPreferenceHelper: SharedPreferenceHelper? = null
        private const val MY_PREFS_NAME = "MidwestSharedPrefs"
        fun getInstance(): SharedPreferenceHelper {
            if (sharedPreferenceHelper == null)
                sharedPreferenceHelper = SharedPreferenceHelper()
            return sharedPreferenceHelper!!
        }
    }

    private fun getSharedPreference(context: Context): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(context)
    }

    private fun getSharedPrefEditor(context: Context): SharedPreferences.Editor? {
        val editor = getSharedPreference(context).edit()
        return editor
    }

    fun saveUserData(context: Context, loginResponseModel: LoginResponseModel) {
        getSharedPrefEditor(context)!!.putString(SharedPrefConstants.USER_DATA, loginResponseModel.toString()).apply()
    }

    fun getUserData(context: Context): LoginResponseModel? {
        val gson = Gson()

        //try to get the LoginResponseModel instance which is saved via sharedpreference contact USER_DATA
        return gson.fromJson<LoginResponseModel>(getSharedPreference(context).getString(SharedPrefConstants.USER_DATA, null), LoginResponseModel::class.java)
        //return LoginResponseModel instance
    }

    fun clearUserData(context: Context){
        getSharedPrefEditor(context)!!.remove(SharedPrefConstants.USER_DATA).apply()
    }

    fun saveDefaultData(context: Context, allDefaultDataModel: AllDefaultDataModel) {
        getSharedPrefEditor(context)!!.putString(SharedPrefConstants.DEFAULT_DATA, allDefaultDataModel.toString()).apply()
    }

    fun getDefaultData(context: Context): AllDefaultDataModel? {
        val gson = Gson()
        return gson.fromJson<AllDefaultDataModel>(getSharedPreference(context).getString(SharedPrefConstants.DEFAULT_DATA, null), AllDefaultDataModel::class.java)
    }

    fun clearDefaultData(context: Context){
        getSharedPrefEditor(context)!!.remove(SharedPrefConstants.DEFAULT_DATA).apply()
    }
}