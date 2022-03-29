package com.mager.gamer.data.remote

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import timber.log.Timber


interface NetworkStateManager {
    fun isOnline(): Boolean
}

class NetworkStateManagerImpl(context: Context) : NetworkStateManager {

    init {
        Timber.e("Network state")
    }

    private val connectivityManager: ConnectivityManager? =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?


    override fun isOnline(): Boolean {
        Timber.d("Network Start check..")
        var result = false
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            connectivityManager?.getNetworkCapabilities(connectivityManager.activeNetwork)?.run {
                result = when {
                    this.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    this.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    this.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                    else -> false
                }
            }
        } else {
            connectivityManager?.run {
                this.activeNetworkInfo?.run {
                    if (type == ConnectivityManager.TYPE_WIFI) {
                        result = true
                    } else if (type == ConnectivityManager.TYPE_MOBILE) {
                        result = true
                    }
                }
            }
        }
        Timber.d("Status Connection ->>>>> $result")
        return result
    }
}