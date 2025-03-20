package com.vtencon.quoteshake.ui.data.newquotation

import android.net.ConnectivityManager
import android.net.NetworkCapabilities.TRANSPORT_CELLULAR
import android.net.NetworkCapabilities.TRANSPORT_WIFI
import javax.inject.Inject

class ConnectivityChecker @Inject constructor(private val connectivityManager: ConnectivityManager) {
    fun isConnectionAvailable(): Boolean{
        // devuelve un Boolean indicando si se logro o no la conexion
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        return capabilities?.hasTransport(TRANSPORT_WIFI) == true ||
                capabilities?.hasTransport(TRANSPORT_CELLULAR) == true
    }
}