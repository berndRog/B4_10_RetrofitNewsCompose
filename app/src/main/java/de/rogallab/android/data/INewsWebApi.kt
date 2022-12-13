package de.rogallab.android.data

import de.rogallab.android.domain.dtos.News
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface INewsWebApi {
   @GET("v2/top-headlines")
   suspend fun getHeadlines(
      @Query("country") country: String = "de",
      @Query("page")    page: Int = 1
   ): Response<News>


   @GET("v2/everything")
   suspend fun getArticles(
      @Query("q")    searchText: String,
      @Query("page") page: Int = 1
   ): Response<News>
}