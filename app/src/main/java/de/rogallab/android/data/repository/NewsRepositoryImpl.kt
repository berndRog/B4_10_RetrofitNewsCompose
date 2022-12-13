package de.rogallab.android.data.repository

import de.rogallab.android.data.IArticlesDao
import de.rogallab.android.data.INewsWebApi
import de.rogallab.android.data.Resource
import de.rogallab.android.domain.ILogger
import de.rogallab.android.domain.INewsRepository
import de.rogallab.android.domain.dtos.News
import de.rogallab.android.domain.entities.Article
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
   private val _articlesDao: IArticlesDao,
   private val _newsWebApi: INewsWebApi,
   private val _dispatcher: CoroutineDispatcher,
   private val _logger:ILogger
) : BaseApiResponse(_logger), INewsRepository {

   init {
      _logger.i(tag, "init{@inject articlesDao ${_articlesDao.hashCode()}}")
      _logger.i(tag, "init{@inject newsWebApi  ${_newsWebApi.hashCode()}}")
      _logger.i(tag, "init{@inject dispatcher  ${_dispatcher.hashCode()}}")
      _logger.i(tag, "init{this ${this.hashCode()}}")
   }

   override fun getHeadlines(country: String, page: Int) = flow {
      _logger.i(tag, "getHeadlines() county:$country page:$page")
      emit(safeApiCall { _newsWebApi.getHeadlines(country, page) })
   }.flowOn(_dispatcher)

   override fun getArticles(searchText: String, page: Int): Flow<Resource<News>> =
      flow {
         _logger.i(tag, "getArticles() search:$searchText, page:$page")
         emit(safeApiCall { _newsWebApi.getArticles(searchText, page) })
      }.flowOn(_dispatcher)


   override fun readAllArticles(): Flow<List<Article>> =  flow<List<Article>> {
         _logger.d(tag, "readAllArticles()")
         _articlesDao.select()
      }.flowOn(_dispatcher)

   override suspend fun readById(id: UUID): Article? = withContext(_dispatcher) {
         _logger.d(tag, "readById()")
         _articlesDao.selectById(id)
      }

   override suspend fun upsert(article: Article) = withContext(_dispatcher) {
         _logger.d(tag, "upsert()")
         _articlesDao.upsert(article)
      }

   override suspend fun remove(article: Article) =
      withContext(_dispatcher) {
         _logger.d(tag, "remove()")
         _articlesDao.delete(article)
      }

   companion object {
                             //12345678901234567890123
      private const val tag = "ok>NewsRepositoryImpl  "
   }
}