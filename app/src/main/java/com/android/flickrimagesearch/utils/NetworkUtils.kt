package com.android.flickrimagesearch.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import com.android.flickrimagesearch.MainActivity

class NetworkUtils {
	companion object {
		fun isNetworkAvailable(mainActivity: MainActivity): Boolean {
			val connectivityManager =
				mainActivity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
			val network = connectivityManager.activeNetwork // network is currently in a high power state for performing data transmission.
			Log.d("Network", "active network $network")
			network ?: return false  // return false if network is null
			val actNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false // return false if Network Capabilities is null
			return when {
				actNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> { // check if wifi is connected
					Log.d("Network", "wifi connected")
					true
				}
				actNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> { // check if mobile dats is connected
					Log.d("Network", "cellular network connected")
					true
				}
				else -> {
					Log.d("Network", "internet not connected")
					false
				}
			}
		}
	}
}