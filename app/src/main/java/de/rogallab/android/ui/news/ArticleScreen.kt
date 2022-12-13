package de.rogallab.android.ui.news

import android.R.id.input
import android.annotation.SuppressLint
import android.util.Patterns
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import de.rogallab.android.R
import de.rogallab.android.domain.entities.Article
import de.rogallab.android.domain.utilities.LogComp
import de.rogallab.android.domain.utilities.LogFun
import de.rogallab.android.ui.comp.BottomNavigationBar
import de.rogallab.android.ui.navigation.NavScreen
import de.rogallab.android.ui.theme.AppTheme
import java.util.regex.Pattern

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun ArticleScreen(
   viewModel: NewsViewModel,
   navController: NavController,
   backRoute: String
) {

   val tag: String = "ok>ArticleScreen      "

   // Check if url is avalible
   var url = ""
   viewModel.article?.let{ article: Article ->
      article?.let {
         url = it.url!!
      }
   }
   // Check if url is admissible
   val urlPattern: Pattern = Patterns.WEB_URL
   if(! urlPattern.matcher(url).matches()) {
      viewModel.onErrorChange("URL ist unzulässig")
   }
   LogComp(tag, "Start $url")

   val scaffoldState: ScaffoldState = rememberScaffoldState()

   AppTheme {
      Scaffold(
         scaffoldState = scaffoldState,
         topBar = {
            TopAppBar(
               title = { Text(text = "Artikel lesen") },
               navigationIcon = {
                  IconButton(onClick = {
                     LogFun(tag, "TopAppBar onClickHandler() back to $backRoute")
                     navController.navigate(route = backRoute) {
                        popUpTo(route = NavScreen.Article.route) { inclusive = true }
                     }
                  }) {
                     Icon(imageVector = Icons.Default.ArrowBack,
                        contentDescription = stringResource(R.string.back))
                  }
               }
            )
         },
         floatingActionButton = {
            FloatingActionButton(
               backgroundColor = MaterialTheme.colors.secondaryVariant,
               onClick = {
                  LogFun(tag, "Save article")
                  viewModel.upsert()
               }
            ) {
               Icon(Icons.Default.Favorite, "Save article")
            }
         },
         floatingActionButtonPosition = FabPosition.End,
         snackbarHost = { snackBarHostState ->
            SnackbarHost(hostState = snackBarHostState) { data ->
               Snackbar(
                  //modifier =  Modifier.border(2.dp, MaterialTheme.colors.secondary),
                  snackbarData = data,
                  actionOnNewLine = true
               )
            }
         },
         bottomBar = { BottomNavigationBar(navController) }
      ) { paddingValues ->

         Column(modifier = Modifier
            .padding(bottom = paddingValues.calculateBottomPadding())
            .fillMaxSize()
         ) {

            AndroidView(factory = {
               WebView(it).apply {
                  layoutParams = ViewGroup.LayoutParams(
                     ViewGroup.LayoutParams.MATCH_PARENT,
                     ViewGroup.LayoutParams.MATCH_PARENT
                  )
                  webViewClient = WebViewClient()
                  loadUrl(url)
               }
            }, update = {
               it.loadUrl(url)
            })
         }
      }
   }
}