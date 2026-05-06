package com.ekodex.manpowerhrms.Others

import android.content.Context
import android.content.Intent
import com.ekodex.manpowerhrms.Login.LoginActivity

class SharedPrefManager private constructor(context: Context) {

    //this method will checker whether user is already logged in or not
    val isLoggedIn: Boolean
        get() {
            val sharedPreferences = ctx?.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
            return sharedPreferences?.getString(KEY_EMAIL, null) != null
        }

    //this method will give the logged in user
    val user: User
        get() {
            val sharedPreferences = ctx?.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
            return User(
                sharedPreferences?.getString(KEY_ID, null)!!,
                sharedPreferences.getString(KEY_EMP_CODE, null)!!,
                sharedPreferences.getString(KEY_FNAME, "NA")!!,
                sharedPreferences.getString(KEY_LNAME, "NA")!!,
                sharedPreferences.getString(KEY_GENDER, "")!!,
                sharedPreferences.getString(KEY_RANK, null)!!,
                sharedPreferences.getString(KEY_EMAIL, null)!!,
                sharedPreferences.getString(KEY_PHONE, null)!!,
                sharedPreferences.getString(KEY_ADDRESS, null)!!,
                sharedPreferences.getString(KEY_STATE, null)!!,
                sharedPreferences.getString(KEY_CITY, null)!!,
                sharedPreferences.getString(KEY_PINCODE, null)!!,
                sharedPreferences.getString(KEY_ONBOARDING, "0")!!,
                sharedPreferences.getString(KEY_ROLE, null)!!,
                sharedPreferences.getString(KEY_CLIENT_ID, null)!!,
                sharedPreferences.getString(KEY_SITE_ID, null)!!,
                sharedPreferences.getString(KEY_CLIENT_CODE, null)!!,
                sharedPreferences.getInt(KEY_SITE_SELECTED, 0)!!,
                sharedPreferences.getString(KEY_COPY_CLIENT_ID, "")!!,
                sharedPreferences.getString(KEY_COPY_SITE_ID, "")!!,
                sharedPreferences.getString(KEY_BANK_NAME, "")!!,
                sharedPreferences.getString(KEY_ACCOUNT_NO, "")!!,
                sharedPreferences.getString(KEY_BANK_IFSC, "")!!,
                sharedPreferences.getString(KEY_AC_HOLDER_NAME, "")!!,
                sharedPreferences.getString(KEY_BANK_ADDRESS, "")!!,
                sharedPreferences.getString(KEY_BANK_STATE, "")!!,
                sharedPreferences.getString(KEY_BANK_CITY, "")!!,
                sharedPreferences.getString(KEY_BANK_MICR, "")!!,
                sharedPreferences.getString(KEY_CARD_NO, "")!!
                )

        }

    init {
        ctx = context
    }

    //this method will store the user data in shared preferences
    fun userLogin(user: User) {
        val sharedPreferences = ctx?.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences?.edit()
        editor?.putString(KEY_ID, user.id)
        editor?.putString(KEY_EMP_CODE, user.emp_code)
        editor?.putString(KEY_FNAME, user.fname)
        editor?.putString(KEY_LNAME, user.lname)
        editor?.putString(KEY_GENDER, user.gender)
        editor?.putString(KEY_RANK, user.rank)
        editor?.putString(KEY_EMAIL, user.email)
        editor?.putString(KEY_PHONE, user.phone)
        editor?.putString(KEY_ADDRESS, user.address)
        editor?.putString(KEY_STATE, user.state)
        editor?.putString(KEY_CITY, user.city)
        editor?.putString(KEY_PINCODE, user.pincode)
        editor?.putString(KEY_ONBOARDING, user.onboarding)
        editor?.putString(KEY_ROLE, user.role)
        editor?.putString(KEY_CLIENT_ID, user.client_id)
        editor?.putString(KEY_SITE_ID, user.site_id)
        editor?.putString(KEY_CLIENT_CODE, user.client_code)
        editor?.putInt(KEY_SITE_SELECTED, user.site_selected)
        editor?.putString(KEY_COPY_CLIENT_ID, user.copy_client_id)
        editor?.putString(KEY_COPY_SITE_ID, user.copy_site_id)
        editor?.putString(KEY_BANK_NAME, user.bank_name)
        editor?.putString(KEY_ACCOUNT_NO, user.account_no)
        editor?.putString(KEY_BANK_IFSC, user.bank_ifsc)
        editor?.putString(KEY_AC_HOLDER_NAME, user.ac_holder_name)
        editor?.putString(KEY_BANK_ADDRESS, user.bank_address)
        editor?.putString(KEY_BANK_STATE, user.bank_state)
        editor?.putString(KEY_BANK_CITY, user.bank_city)
        editor?.putString(KEY_BANK_MICR, user.bank_micr)
        editor?.putString(KEY_CARD_NO, user.card_no)

        editor?.apply()
    }

    //this method will logout the user
    fun logout() {
        val sharedPreferences = ctx?.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences?.edit()
        editor?.clear()
        editor?.apply()
        val intent = Intent(ctx, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        ctx?.startActivity(intent)
    }

    companion object {
        val SHARED_PREF_NAME = "volleyregisterlogin"
        val KEY_ID = "keyid"
         val KEY_EMP_CODE = "keyempcode"
         val KEY_FNAME = "keyfname"
         val KEY_LNAME = "keylname"
         val KEY_GENDER = "keygender"
        val KEY_RANK = "keyRank"
        val KEY_EMAIL = "keyEmail"
        val KEY_PHONE = "keyPhone"
        val KEY_ADDRESS = "keyAddress"
        val KEY_STATE = "keyState"
        val KEY_CITY = "keyCity"
        val KEY_PINCODE = "keyPincode"
        val KEY_ONBOARDING = "keyOnboarding"
        val KEY_ROLE = "keyRole"
        val KEY_CLIENT_ID = "keyClientId"
        val KEY_SITE_ID = "keySiteId"
        val KEY_CLIENT_CODE = "keyClientCode"
        val KEY_SITE_SELECTED = "keySiteSelected"
        val KEY_COPY_CLIENT_ID = "keyCopyClientId"
        val KEY_COPY_SITE_ID = "keyCopySiteId"
        val KEY_BANK_NAME = "keyBankName"
        val KEY_ACCOUNT_NO = "keyAccountNo"
        val KEY_BANK_IFSC = "keyBankIfsc"
        val KEY_AC_HOLDER_NAME = "keyAcHolderName"
        val KEY_BANK_ADDRESS = "keyBankAddress"
        val KEY_BANK_STATE = "keyBankState"
        val KEY_BANK_CITY = "keyBankCity"
        val KEY_BANK_MICR = "keyBankMicr"
        val KEY_CARD_NO = "keyCardNo"


        private var mInstance: SharedPrefManager? = null
        var ctx: Context? = null
        @Synchronized
        fun getInstance(context: Context): SharedPrefManager {
            if (mInstance == null) {
                mInstance = SharedPrefManager(context)
            }
            return mInstance as SharedPrefManager
        }
    }
}