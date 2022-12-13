package de.rogallab.android.ui.news

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.navigation.NavController
import de.rogallab.android.R
import de.rogallab.android.domain.usecases.UiState
import de.rogallab.android.domain.utilities.LogFun
import de.rogallab.android.ui.comp.BottomNavigationBar
import de.rogallab.android.ui.navigation.NavScreen
import de.rogallab.android.ui.theme.AppTheme
import de.rogallab.android.ui.theme.paddings
import kotlinx.coroutines.delay

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ArticlesListScreen(
   viewModel: NewsViewModel,
   navController: NavController
) {
   // 12345678901234567890123
   val tag = "ok>ArticlesListScreen  "

   LaunchedEffect(key1 = viewModel.startSearchArticles) {
      if(!viewModel.searchText.isNullOrEmpty()) {
         LogFun(tag, "launchEffect searchArticles() ${viewModel.searchText}")
         viewModel.searchArticles(viewModel.searchText)
      }
   }

   val scaffoldState: ScaffoldState = rememberScaffoldState()

   AppTheme {
      Scaffold(
         scaffoldState = scaffoldState,
         topBar = {
            TopAppBar(
               title = { Text(text = "Artikel lesen") },
               navigationIcon = {
                  IconButton(onClick = {
                     LogFun(tag, "TopAppBar onClickHandler()")
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
         }
      ) { paddingValues ->

         Column(modifier = Modifier
            .padding(bottom = paddingValues.calculateBottomPadding())
            .padding(horizontal = MaterialTheme.paddings.tiny)
            .fillMaxSize()
         ) {

            OutlinedTextField(
               modifier = Modifier.fillMaxWidth(),
               value = viewModel.searchText,
               onValueChange = {
                  viewModel.onSearchTextChange(it)
               },
               label = { Text(text = stringResource(R.string.name)) },
               leadingIcon = {
                  Icon(imageVector = Icons.Outlined.Search,
                     contentDescription = "Search Articles")
               },
               keyboardOptions = KeyboardOptions(
                  keyboardType = KeyboardType.Text,
                  imeAction = ImeAction.Search
               ),
               keyboardActions = KeyboardActions(
                  onSearch = {
                     viewModel.onStartSearchArticlesChange(!viewModel.startSearchArticles)
                  }
               ),
               textStyle = MaterialTheme.typography.h6,
               singleLine = true,
            )

            // observer
            val uiState: UiState by viewModel.stateArticles.collectAsState(UiState.Empty)

            if(uiState != UiState.Empty)
               ArticlesList(
                  uiState, viewModel, navController, scaffoldState,
                  NavScreen.Articles.route, tag
               )
         }
      }
   }
}