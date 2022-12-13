package de.rogallab.android.domain.entities

import androidx.room.*
import de.rogallab.android.domain.dtos.Source
import java.io.Serializable
import java.util.*

@Entity(tableName="article")
data class Article(
   val author:      String? = "",
   val content:     String? = "",
   val description: String? = "",
   val publishedAt: String? = "",
   val source:      Source? = null,
   val title:       String? = "",
   val url:         String? = "",
   val urlToImage:  String? = "",
   @PrimaryKey
   val id :UUID = UUID.randomUUID()
): Serializable
