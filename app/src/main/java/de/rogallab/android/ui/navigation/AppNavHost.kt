package de.rogallab.android.ui.navigation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import de.rogallab.android.domain.utilities.LogComp
import de.rogallab.android.ui.news.*

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AppNavHost(
   viewModel: NewsViewModel = hiltViewModel()
) {

            //12345678901234567890123
   val tag = "ok>AppNavHost          "

   // Any -> NavController -> NavHostController
   // NavHostController is a Subclass of NavController that offers additional APIs
   // for use by a NavHost to connect the NavController to external dependencies.
   val navHostController: NavHostController = rememberNavController()

   NavHost(
      navController = navHostController ,
      startDestination = NavScreen.Headlines.route
   ) {

      composable(route = NavScreen.Headlines.route) {
         LogComp(tag, "HeadLinesScreen route=${NavScreen.Headlines.route}")
         HeadLinesScreen(
            viewModel = viewModel,
            navController = navHostController
         )
      }

      composable(route = NavScreen.Articles.route) {
         LogComp(tag, "ArticlesListScreen route=${NavScreen.Articles.route}")
         ArticlesListScreen(
            viewModel = viewModel,
            navController = navHostController
         )
      }

      composable(route = NavScreen.SavedArticles.route) {
         LogComp(tag, "SavedListScreen route=${NavScreen.SavedArticles.route}")
         SavedListScreen(
            viewModel = viewModel,
            navController = navHostController
         )
      }

      composable(
         route = NavScreen.Article.route+"/{backRoute}",
         arguments = listOf(navArgument("backRoute") { type = NavType.StringType } )
      ) { backStackEntry ->
         val backRoute = backStackEntry.arguments?.getString("backRoute") ?: ""
         LogComp(tag, "ArticleScreen route=${NavScreen.Article.route} backRoute=$backRoute")
         ArticleScreen(
            viewModel = viewModel,
            navController = navHostController,
            backRoute = backRoute
         )}
   }
}