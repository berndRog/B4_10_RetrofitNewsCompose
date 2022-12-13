package de.rogallab.android.domain.dtos

import de.rogallab.android.domain.entities.Article

data class News(
   val articles: List<Article>?,
   val status: String?,
   val totalResults: Int?
)