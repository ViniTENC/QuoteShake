package com.vtencon.quoteshake.ui.data.newquotation

import android.net.ConnectivityManager
import android.net.NetworkCapabilities.TRANSPORT_CELLULAR
import android.net.NetworkCapabilities.TRANSPORT_WIFI
import javax.inject.Inject

class ConnectivityChecker @Inject constructor(private val connectivityManager: ConnectivityManager) {
    fun isConnectionAvailable(): Boolean{
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities?.hasTransport(TRANSPORT_WIFI) == true ||
            capabilities?.hasTransport(TRANSPORT_CELLULAR) == true
        ){
            return true
        }
        return false
    }
}