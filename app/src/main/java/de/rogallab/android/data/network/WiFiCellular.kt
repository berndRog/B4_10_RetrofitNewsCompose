package de.rogallab.android.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import de.rogallab.android.domain.ILogger
import javax.inject.Inject

class WiFiCellular @Inject constructor(
   private val _context: Context,
   private val _logger: ILogger
) {

   private val connectivityManager =
      _context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

   init {
      _logger.i(tag, "init{this ${this.hashCode()}}")
   }

   fun isWiFiOnline(): Boolean {
      var result = false
      if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
         // SDK >= 23 (Android 6.0)
         val network = connectivityManager.activeNetwork
         connectivityManager.getNetworkCapabilities(network)?.let {
            result = it.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
         }
      }
      return result
   }

   fun isCellularOnline(): Boolean {
      var result = false
      if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
         // SDK >= 23 (Android 6.0)
         val network = connectivityManager.activeNetwork
         connectivityManager.getNetworkCapabilities(network)?.let {
            result = it.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
         }
      }
      return result
   }

   companion object {
                             //12345678901234567890123
      private const val tag = "ok>WiFiCellular       ."

   }
}