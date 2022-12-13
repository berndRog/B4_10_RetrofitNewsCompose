package de.rogallab.android.data.network

import de.rogallab.android.AppStart
import de.rogallab.android.domain.ILogger
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class WebApiKeyInterceptor(
   private val _logger: ILogger
) : Interceptor {

   init {
      _logger.i(tag, "init{this ${this.hashCode()}}")
   }

   override fun intercept(chain: Interceptor.Chain): Response {
      val original: Request = chain.request()
      val request: Request = original.newBuilder()
         .header("X-API-Key", AppStart.api_key)
         //          .header("X-Session", getServerSession())
         .method(original.method, original.body)
         .build();
      return chain.proceed(request);
   }

   companion object {
                             //12345678901234567890123
      private const val tag = "Ok>WebApiKeyIntercept ."

   }
}