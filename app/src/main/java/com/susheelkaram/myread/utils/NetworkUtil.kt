package com.susheelkaram.trackky.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

/**
 * Created by Susheel Kumar Karam
 * Website - SusheelKaram.com
 */
class NetworkUtil {

    companion object {

        @Suppress("DEPRECATION")
        fun isInternetAvailable(context: Context): Boolean {
            var isInternetAvailable = false
            val connectivityManager: ConnectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
                var networkInfo = connectivityManager.activeNetworkInfo
                isInternetAvailable = networkInfo.isConnected
            }
            else {
                var allNetworks = connectivityManager.allNetworks
                for(network in allNetworks) {
                    val capabilities = connectivityManager.getNetworkCapabilities(network)
                    if(capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)){
                        isInternetAvailable = true
                        break;
                    }
                }
            }

            return isInternetAvailable
        }
    }
}