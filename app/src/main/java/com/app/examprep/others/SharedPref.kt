package com.bulltradeintraday.app.others

import android.R
import android.content.Context
import androidx.appcompat.widget.Toolbar
import com.app.examprep.MainActivity
import com.app.examprep.data.CourseData
import com.app.examprep.fragment.LandingPageFragment
import com.google.gson.Gson

class SharedPref(private val context: Context) {

    val sharedPref = context.getSharedPreferences("examprep_pref", Context.MODE_PRIVATE)
    val editor = sharedPref.edit()

    val USERAUTHSTATUS = "UserAuthStatus"
    val SUBSCRIBEDPREF = "UserSubscription"
    val USERNAME = "UserName"
    val USERMOBILENUMBER = "UserMobilenumber"
    val PROFILEPICURL = "ProfilePicUrl"
    val COURSEDATA = "CourseData"
    val PAYMENTERROR = "PaymentError"
    val ORDERID = "OrderId"

    fun setUserAuthStatus(status: Boolean) {
        editor.apply {
            putBoolean(USERAUTHSTATUS, status)
            apply()
        }
    }


    fun getUserAuthStatus(): Boolean {
        return sharedPref.getBoolean(USERAUTHSTATUS, false)
    }

    fun saveUserName(name : String) {
        editor.apply {
            putString(USERNAME,name)
            apply()
        }
    }


    fun saveMobileNumber(mobile : String) {
        editor.apply {
            putString(USERMOBILENUMBER,mobile)
            apply()
        }
    }

    fun saveProfilePicUrl(profilePicUrl : String) {
        editor.apply {
            putString(PROFILEPICURL,profilePicUrl)
            apply()
        }
    }

    fun getUserName() : String{
        return  sharedPref.getString(USERNAME,"null") ?: "null"
    }

    fun getMobileNumber() : String{
        return  sharedPref.getString(USERMOBILENUMBER,"null") ?: "null"
    }

    fun getProfilePicUrl() : String{
        return  sharedPref.getString(PROFILEPICURL,"null") ?: "null"
    }

    fun updatePaymentError(status : Boolean) {
        editor.apply {
            putBoolean(PAYMENTERROR,status)
            apply()
        }
    }



    fun updateOrderID(orderId : String) {
        editor.apply {
            putString(ORDERID,orderId)
            apply()
        }
    }

    fun getOrderID() : String{
        return  sharedPref.getString(ORDERID,"null") ?: "null"
    }

    fun getPaymentError() : Boolean{
        return  sharedPref.getBoolean(PAYMENTERROR,false)
    }

    fun setCourseData(courseData: CourseData){
        val gson =  Gson()
        val json = gson.toJson(courseData)
        editor.putString(COURSEDATA, json);
        editor.commit();
    }

    fun getCourseData() : CourseData?{
        val gson = Gson()
        val json = sharedPref.getString(COURSEDATA, "null")
        return gson.fromJson(json, CourseData::class.java)
    }

}