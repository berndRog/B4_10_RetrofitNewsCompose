package de.rogallab.android.data.network

import de.rogallab.android.domain.ILogger
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject

class WebConnectivityInterceptor @Inject constructor(
   private val _wiFiCellular: WiFiCellular,
   private val _logger: ILogger
) : Interceptor {

   init {
      _logger.i(tag, "init{this ${this.hashCode()}}")
   }

   override fun intercept(chain: Interceptor.Chain): Response {
      // Check
      if (_wiFiCellular.isWiFiOnline() || _wiFiCellular.isCellularOnline()) {
         val original: Request = chain.request()
         return chain.proceed(original)
      } else {
         throw IOException("Cellular and Wifi are not connected")
      }
   }

   companion object {
                             //12345678901234567890123.
      private const val tag = "ok>WebConnectIntercept."

   }
}