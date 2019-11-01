package com.midwestpilotcars.models.loginModels

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.Gson
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class LoginResponseModel() :Parcelable {

    @Expose
    var data: Data? = null
    @Expose
    var message: String? = null
    @Expose
    var status: Long? = null

    constructor(parcel: Parcel) : this() {
        message = parcel.readString()
        status = parcel.readValue(Long::class.java.classLoader) as? Long
    }

    class Data {

        @SerializedName("AuthenticateToken")
        var authenticateToken: String? = null
        @SerializedName("user_email")
        var userEmail: String? = null
        @SerializedName("user_id")
        var userId: String? = null
        @SerializedName("user_name")
        var userName: String? = null
        @SerializedName("user_phone")
        var userPhone: String? = null
        @SerializedName("user_type")
        var userType: String? = null

    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(message)
        parcel.writeValue(status)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<LoginResponseModel> {
        override fun createFromParcel(parcel: Parcel): LoginResponseModel {
            return LoginResponseModel(parcel)
        }

        override fun newArray(size: Int): Array<LoginResponseModel?> {
            return arrayOfNulls(size)
        }
    }

    override fun toString(): String {
        val gson = Gson()
        return gson.toJson(this)
    }
}
