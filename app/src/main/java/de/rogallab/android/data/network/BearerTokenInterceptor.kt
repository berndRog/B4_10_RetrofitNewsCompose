package de.rogallab.android.data.network

import de.rogallab.android.domain.ILogger
import okhttp3.Interceptor
import okhttp3.Response


class BearerTokenInterceptor(
   private val _logger: ILogger
) : Interceptor {
   var token: String = "";
   override fun intercept(chain: Interceptor.Chain): Response {
      var request = chain.request()
      if (request.header("No-Authentication") == null) {
         if (!token.isNullOrEmpty()) {
            val finalToken = "Bearer " + token
            request = request.newBuilder()
               .addHeader("Authorization", finalToken)
               .build()
         }
      }
      return chain.proceed(request)
   }
}