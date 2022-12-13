package de.rogallab.android.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import de.rogallab.android.AppStart
import de.rogallab.android.data.INewsWebApi
import de.rogallab.android.data.database.AppDatabase
import de.rogallab.android.data.IArticlesDao
import de.rogallab.android.data.network.*
import de.rogallab.android.domain.ILogger
import de.rogallab.android.domain.utilities.LoggerImplCat
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppProvideModules {
                           //12345678901234567890123
    private const val tag = "ok>AppProvideModules   "

    @Singleton
    @Provides
    fun provideLogger(
    ): ILogger {
        val logger = LoggerImplCat  // = kotlin object
        logger.i(tag, "provideLogger()")
        return logger
    }
    @Singleton
    @Provides
    fun provideContext(
       application: Application, // provided by Hilt
       logger:ILogger
    ): Context {
        logger.i(tag,"provideContext()")
        return application.applicationContext
    }
    @Provides
    @Singleton
    fun provideDispatcher(
       logger: ILogger
    ): CoroutineDispatcher {
        logger.i(tag,"provideDispatcher()")
        return Dispatchers.IO
    }

    @Singleton
    @Provides
    fun provideLoggingInterceptor(
       logger: ILogger
    ): HttpLoggingInterceptor {
        logger.i(tag, "provideLoggingInterceptor()")
        return HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }
    }
    @Provides
    @Singleton
    fun provideWifiCellular(
        context: Context,
        logger:ILogger
    ): WiFiCellular {
        logger.i(tag,"provideWifiCellular()")
        return WiFiCellular(context,logger)
    }
    @Provides
    @Singleton
    fun provideWebConnectivityInterceptor(
       wiFiCellular: WiFiCellular,
       logger: ILogger
    ): WebConnectivityInterceptor {
        logger.i(tag,"provideWebConnectivityInterceptor()")
        return WebConnectivityInterceptor(wiFiCellular,logger)
    }
    @Provides
    @Singleton
    fun provideWebApiKeyInterceptor(
       logger: ILogger
    ): WebApiKeyInterceptor {
        logger.i(tag,"provideWebApiKeyInterceptor()")
        return WebApiKeyInterceptor(logger)
    }
    @Provides
    @Singleton
    fun provideOkHttpClient(
       apiKeyInterceptor: WebApiKeyInterceptor,
       connectivityInterceptor: WebConnectivityInterceptor,
       loggingInterceptor: HttpLoggingInterceptor,
       logger: ILogger
    ) : OkHttpClient {
        logger.i(tag, "provideOkHttpClient()")
        return OkHttpClient.Builder()
           .connectTimeout(30, TimeUnit.SECONDS)
           .readTimeout(5, TimeUnit.SECONDS)
           .writeTimeout(5, TimeUnit.SECONDS)
//         .authenticator(AccessTokenAuthenticator(accessTokenProvider))
           .addInterceptor(apiKeyInterceptor)
           .addInterceptor(connectivityInterceptor)
           .addInterceptor(loggingInterceptor)
           .build()
    }
    @Provides
    @Singleton
    fun provideRetrofit(
       okHttpClient: OkHttpClient,
       logger: ILogger
    ): Retrofit {
        logger.i(tag, "provideRetrofit()")
        return Retrofit.Builder()
           .baseUrl(AppStart.base_url)
           .addConverterFactory(GsonConverterFactory.create())
           .client(okHttpClient)
           .build()
    }
    @Provides
    @Singleton
    fun provideNewsWebApiClient(
       retrofit: Retrofit,
       logger: ILogger
    ): INewsWebApi {
        logger.i(tag, "provideNewsWebApiImpl()")
        return retrofit.create(INewsWebApi::class.java)
    }

    @Provides
    @Singleton
    fun provideAppDatabase(
        application: Application, // provided by Hilt
        logger:ILogger
    ): AppDatabase {
        logger.i(tag,"provideAppDatabase()")
        return Room.databaseBuilder(
            application.applicationContext,
            AppDatabase::class.java,
            AppStart.database_name
        ).fallbackToDestructiveMigration()
         .build()
    }

    @Provides
    @Singleton
    fun provideArticleDao(
        database: AppDatabase,
        logger: ILogger
    ): IArticlesDao {
       logger.i(tag,"provideArticleDao()")
       return database.createArticleDao()
    }

//    @Provides
//    @Singleton
//    fun provideNewsRepository (
//       articleDao: IArticlesDao,
//       newsWebservice: INewsWebApi,
//       dispatcher: CoroutineDispatcher,
//       logger: ILogger
//    ): INewsRepository {
//        return NewsRepositoryImpl(articleDao, newsWebservice, dispatcher, logger)
//    }
}
