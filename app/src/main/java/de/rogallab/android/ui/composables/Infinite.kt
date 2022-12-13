package de.rogallab.android.ui.composables
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.*
import de.rogallab.android.domain.entities.Article
import de.rogallab.android.domain.utilities.LogComp
import de.rogallab.android.domain.utilities.LogFun
import de.rogallab.android.ui.news.NewsItem
import kotlinx.coroutines.flow.distinctUntilChanged

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun InfiniteList(
   listItems: List<Article>,
   onLoadMore: () -> Unit
) {
   val listState = rememberLazyListState()

   LazyColumn(
      state = listState
   ) {
      items(
         items = listItems

      ) { article ->
         NewsItem(
            title = article.title,
            description = article.description,
            url = article.url,
            imagePath = article.urlToImage,
            publishedAt = article.publishedAt,
            onClick = {  }
         )
      }
   }

   InfiniteListHandler(listState = listState) {
      onLoadMore()
   }
}

/**
 * Handler to make any lazy column (or lazy row) infinite. Will notify the [onLoadMore]
 * callback once needed
 * @param listState state of the list that needs to also be passed to the LazyColumn composable.
 * Get it by calling rememberLazyListState()
 * @param buffer the number of items before the end of the list to call the onLoadMore callback
 * @param onLoadMore will notify when we need to load more
 *
 * https://dev.to/luismierez/infinite-lazycolumn-in-jetpack-compose-44a4*
 */
@Composable
fun InfiniteListHandler(
   listState: LazyListState,
   buffer: Int = 2,
   onLoadMore: () -> Unit
) {
   val loadMore: State<Boolean> = remember {
      derivedStateOf {
         val layoutInfo = listState.layoutInfo
         val totalItemsNumber = layoutInfo.totalItemsCount
         val lastVisibleItemIndex = (layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0) + 1
         LogFun("ok>InfiniteListHandler   .", "totalItemsNumber ${totalItemsNumber}" )
         LogFun("ok>InfiniteListHandler   .", "lastVisibleItemIndex ${lastVisibleItemIndex}" )
         lastVisibleItemIndex > (totalItemsNumber - buffer) // ^derivedStateOf:Boolean
      }
   }

   LaunchedEffect(loadMore) {
      LogFun("ok>InfiniteListHandler   .", "${loadMore.value}" )
      snapshotFlow { loadMore.value }
         .distinctUntilChanged()
         .collect {
            onLoadMore()
         }
   }
}