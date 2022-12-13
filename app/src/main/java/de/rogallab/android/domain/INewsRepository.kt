package de.rogallab.android.domain

import de.rogallab.android.data.Resource
import de.rogallab.android.domain.entities.Article
import de.rogallab.android.domain.dtos.News
import kotlinx.coroutines.flow.Flow
import java.util.*

interface INewsRepository {

   fun getHeadlines(countryCode: String, page: Int): Flow<Resource<News>>
   fun getArticles(searchText: String, page: Int): Flow<Resource<News>>
   fun readAllArticles(): Flow<List<Article>>
   suspend fun readById(id: UUID): Article?
   suspend fun upsert(article: Article)
   suspend fun remove(article: Article)

}