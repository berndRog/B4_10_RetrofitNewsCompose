package de.rogallab.android.data

import androidx.room.*
import de.rogallab.android.domain.entities.Article
import kotlinx.coroutines.flow.Flow
import java.util.UUID

@Dao
interface IArticlesDao {

   @Query("SELECT * FROM article")
   fun select(): Flow<List<Article>>

   @Query("SELECT * FROM article WHERE id = :id ")
   suspend fun selectById(id: UUID): Article?

   @Insert(onConflict = OnConflictStrategy.REPLACE)
   suspend fun upsert(article: Article)

   @Delete
   suspend fun delete(article: Article)

}