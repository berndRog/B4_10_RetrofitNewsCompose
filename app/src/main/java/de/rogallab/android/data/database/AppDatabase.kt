package de.rogallab.android.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import de.rogallab.android.data.IArticlesDao
import de.rogallab.android.AppStart
import de.rogallab.android.domain.entities.Article

@Database(entities = arrayOf(Article::class),
          version = AppStart.database_version,
          exportSchema = false)
@TypeConverters(Converters::class)

abstract class AppDatabase: RoomDatabase() {

    abstract fun createArticleDao(): IArticlesDao

}