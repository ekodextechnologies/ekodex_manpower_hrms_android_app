package com.ekodex.manpowerhrms.Others

import android.content.Context
import android.net.ConnectivityManager

class InternetNew {
    fun isConnected(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = cm.activeNetworkInfo
        return network != null && network.isConnected
    }
}
