package de.rogallab.android.ui.news

import android.app.Activity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import de.rogallab.android.R
import de.rogallab.android.domain.entities.Article
import de.rogallab.android.domain.usecases.UiState
import de.rogallab.android.domain.utilities.LogComp
import de.rogallab.android.domain.utilities.LogFun
import de.rogallab.android.ui.comp.BottomNavigationBar
import de.rogallab.android.ui.composables.ShowErrorMessage
import de.rogallab.android.ui.navigation.NavScreen
import de.rogallab.android.ui.theme.AppTheme
import de.rogallab.android.ui.theme.paddings

@ExperimentalMaterialApi
@Composable
fun SavedListScreen(
   viewModel: NewsViewModel,
   navController: NavController
) {
   // 12345678901234567890123
   val tag = "ok>SavedArticlesScreen "

   LaunchedEffect(key1 = Unit) {
      LogFun(tag,"launchEffect readSavedArticles() ${viewModel.searchText}")
      viewModel.readSavedArticles()
   }

   // observer
   val uiState: UiState by viewModel.stateSavedArticles.collectAsState(UiState.Empty)

   val scaffoldState: ScaffoldState = rememberScaffoldState()

   AppTheme {
      Scaffold(
         scaffoldState = scaffoldState,
         topBar = {
            TopAppBar(
               title = { Text(stringResource(R.string.savedArticles)) },
               navigationIcon = {
                  IconButton(onClick = {
                     LogFun(tag, "TopAppBar back navigation")
                     navController.navigate(route = NavScreen.Headlines.route) {
                        popUpTo(route = NavScreen.Article.route) { inclusive = true }
                     }
                  }) {
                     Icon(imageVector = Icons.Default.ArrowBack,
                        contentDescription = stringResource(R.string.back))
                  }
               }
            )
         },
         snackbarHost = { snackBarHostState ->
            SnackbarHost(hostState = snackBarHostState) { data ->
               Snackbar(
                  //modifier =  Modifier.border(2.dp, MaterialTheme.colors.secondary),
                  snackbarData = data,
                  actionOnNewLine = true
               )
            }
         },
         bottomBar = {
            BottomNavigationBar(navController)
         },
      ) { paddingValues ->

         Column(modifier = Modifier
            .padding(bottom = paddingValues.calculateBottomPadding())
            .padding(horizontal = MaterialTheme.paddings.tiny)
            .fillMaxSize()
         ) {
            ArticlesList(uiState, viewModel, navController, scaffoldState, tag)
         }
      }
   }
}