package de.rogallab.android.ui.news

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import de.rogallab.android.domain.entities.Article
import de.rogallab.android.domain.usecases.UiState
import de.rogallab.android.domain.utilities.LogComp
import de.rogallab.android.ui.composables.ShowErrorMessage
import de.rogallab.android.ui.navigation.NavScreen

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ArticlesList(
   uiState: UiState,
   viewModel: NewsViewModel,
   navController: NavController,
   scaffoldState: ScaffoldState,
   backRoute: String,
   tag: String,
) {

   when (uiState) {

      UiState.Empty -> {
         LogComp(tag, "UiState.Empty")
      }

      UiState.Loading -> {
         LogComp(tag, "UiState.Loading")
         Column(modifier = Modifier
            .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
         ) {
            CircularProgressIndicator(modifier = Modifier.size(150.dp))
         }
      }

      UiState.Retrying -> {
         LogComp(tag, "UiState.Retrying")
      }

      // error handling for UiState.Error
      is UiState.Error -> {
         var message = (uiState as UiState.Error).message
         LogComp(tag, "UiState.Error $message")
         viewModel.onErrorChange(message)
         ShowErrorMessage(
            scaffoldState = scaffoldState,                   // State ↓
            errorMessage = viewModel.errorMessage,           // State ↓
            actionLabel = "Wiederholen",                     // State ↓
            onErrorDismiss = { viewModel.onErrorDismiss() }, // Event ↑
            onErrorAction = { viewModel.onErrorAction() },   // Event ↑
         )
      }

      is UiState.Success<*> -> {
         LogComp(tag, "UiState.Success")
         var articles = (uiState as UiState.Success<*>).data as MutableList<Article>

         LazyColumn(
            state = rememberLazyListState()
         ) {

            items(
               items = articles,
               key = { it.id }
            ) { article ->
               LogComp(tag, "${article.title}")
               NewsItem(
                  title = article.title,
                  description = article.description,
                  url = article.url,
                  imagePath = article.urlToImage,
                  publishedAt = article.publishedAt,

                  onClick = { url ->
                     viewModel.article = article
                     val route = NavScreen.Article.route + "/$backRoute"
                     navController.navigate(route = NavScreen.Article.route + "/$backRoute") {
                        popUpTo(route = backRoute) { inclusive = true }
                     }
                  }
               )
            }
//                   InfiniteList(
//                      listItems = articles,
//                      onLoadMore = { viewModel.searchHeadlines("de",2)  }
//                   )
         }
      }
   }
}