package de.rogallab.android.data.repository

import de.rogallab.android.data.Resource
import de.rogallab.android.domain.ILogger
import de.rogallab.android.domain.dtos.News
import retrofit2.Response

abstract class BaseApiResponse (
   private val _logger: ILogger
) {

   suspend fun <T> safeApiCall(
      apiCall: suspend () -> Response<T>  // Functiontyp
   ): Resource<T> {

      try {

         // send request and receive response
         val response: Response<T> = apiCall()

         logResponse("ok>BaseApiResponse    ", response)

         if (response.isSuccessful) {
            val body = response.body()
            body?.let {
               return Resource.Success(it)
            }
         }

         return Resource.Error("${response.code()}: ${HttpStatusMessage(response.code())}")
      }
      catch (e: Exception) {
         return Resource.Error("Api call failed ${e.message}")
      }
   }

   private fun <T> logResponse(
      tag: String,
      response: Response<T>
   ) {
      _logger.v(tag, "Request ${response.raw().request.method} ${response.raw().request.url}")
      _logger.v(tag, "Request Headers")
      response.raw().request.headers.forEach {
         val text = "%-15s %s".format(it.first, it.second)
         _logger.v(tag, "$text")
      }

      val ms = response.raw().receivedResponseAtMillis - response.raw().sentRequestAtMillis
      _logger.v(tag, "took $ms ms")
      _logger.v(tag, "Response isSuccessful ${response.isSuccessful()}")

      _logger.v(tag, "Response Headers")
      response.raw().headers.forEach {
         val text = "%-15s %s".format(it.first, it.second)
         _logger.v(tag, "$text")
      }

      if (response.body() is News) {
         val news = response.body() as News
         val text = response.body().toString().substring(0,200)
         _logger.v(tag, "Response Body         $text")
         _logger.v(tag, "NewsApi.articles.size ${news.articles?.size}")
         _logger.v(tag, "NewsApi.status        ${news.status}")
         _logger.v(tag, "NewsApi.totalResults  ${news?.totalResults}")

      }
      _logger.v(tag, "Response Status Code ${response.code()}")
      _logger.v(tag, "Response Status Message ${HttpStatusMessage(response.code())}")

   }

   private fun HttpStatusMessage(httpStatusCode:Int) : String =
      when(httpStatusCode) {
         200 -> "OK"
         201 -> "Created"
         202 -> "Accepted"
         204 -> "No Content"
         400 -> "Bad Request"
         401 -> "Unauthorized"
         403 -> "Forbidden"
         404 -> "Not Found"
         405 -> "Method Not Allowed"
         406 -> "Not Acceptable"
         407 -> "Proxy Authentication Required"
         408 -> "Request Timeout"
         409 -> "Conflict"
         415 -> "Unsupported Media Type"
         500 -> "Internal Server Error"
         501 -> "Not Implemented"
         502 -> "Bad Gateway"
         503 -> "Service Unavailable"
         504 -> "Gateway Timeout"
         else -> "Unknown"
      }

}
