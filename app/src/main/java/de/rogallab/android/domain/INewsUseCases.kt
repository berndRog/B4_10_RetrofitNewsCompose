package de.rogallab.android.domain

import de.rogallab.android.domain.usecases.FetchArticles
import de.rogallab.android.domain.usecases.FetchHeadlines
import de.rogallab.android.domain.usecases.ReadAllArticles
import de.rogallab.android.domain.usecases.ReadArticleById

interface INewsUseCases {
   val fetchHeadlines: FetchHeadlines
   val fetchArticles: FetchArticles
   val readAllArticles: ReadAllArticles
   val readArticleById: ReadArticleById
}