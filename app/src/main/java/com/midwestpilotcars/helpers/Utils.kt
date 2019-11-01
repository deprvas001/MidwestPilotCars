package com.midwestpilotcars.helpers

import android.annotation.SuppressLint
import android.provider.Settings
import com.midwestpilotcars.MidwestPilotCars
import android.content.Intent
import android.os.Parcelable
import android.content.Context
import android.support.v4.graphics.TypefaceCompatUtil.getTempFile
import android.provider.MediaStore
import android.support.v4.content.FileProvider
import android.view.View
import android.widget.EditText
import android.net.NetworkInfo
import android.support.v4.content.ContextCompat.getSystemService
import android.net.ConnectivityManager


class Utils {
    companion object {
        fun getDeviceId(): String {
            val deviceId = Settings.Secure.getString(MidwestPilotCars.getInstance().applicationContext.contentResolver,
                    Settings.Secure.ANDROID_ID)
            return deviceId;
        }
        private fun addIntentsToList(context: Context, list: MutableList<Intent>, intent: Intent): MutableList<Intent> {
            val resInfo = context.packageManager.queryIntentActivities(intent, 0)
            for (resolveInfo in resInfo) {
                val packageName = resolveInfo.activityInfo.packageName
                val targetedIntent = Intent(intent)
                targetedIntent.setPackage(packageName)
                list.add(targetedIntent)
            }
            return list
        }

        fun toggleView(editText: View) {
            if (editText.visibility == View.VISIBLE) {
                editText.visibility = View.GONE
            } else {
                editText.visibility = View.VISIBLE

            }
        }

        public fun isNetworkAvailable(context: Context): Boolean {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
            val activeNetworkInfo = connectivityManager!!.activeNetworkInfo
            return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting
        }
    }

}