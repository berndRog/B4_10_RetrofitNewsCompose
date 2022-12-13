package de.rogallab.android

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import de.rogallab.android.domain.ILogger
import javax.inject.Inject

@HiltAndroidApp
class AppStart : Application() {

   @Inject
   lateinit var logger: ILogger

   override fun onCreate() {
      super.onCreate()
      logger.i(tag,"onCreate()")
   }


   companion object {
      //                       12345678901234567890123
      private const val tag = "ok>AppStart           ."
      const val database_name:    String = "B4_10_RetrofitNewsCompose.db"
      const val database_version: Int    = 1
      const val URL:              String = "http://10.0.2.2:5000/api/v1.0/"


      const val base_url: String = "https://newsapi.org/"
      const val api_key:  String = "a904cda52f054306a6cc9a3494b36aad"
      const val ARTICLE_SEARCH_TIME_DELAY = 1000L

   }

}