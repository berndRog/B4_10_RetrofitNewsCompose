package de.rogallab.android.domain.usecases

import de.rogallab.android.domain.INewsUseCases
import javax.inject.Inject

data class NewsUseCasesImpl @Inject constructor(
   override val fetchHeadlines: FetchHeadlines,
   override val fetchArticles: FetchArticles,
   override val readAllArticles: ReadAllArticles,
   override val readArticleById: ReadArticleById
) : INewsUseCases