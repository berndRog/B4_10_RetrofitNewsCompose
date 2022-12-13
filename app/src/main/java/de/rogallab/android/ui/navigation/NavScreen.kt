package de.rogallab.android.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavScreen(
   val route: String,
   var icon: ImageVector = Icons.Outlined.Menu,
   var title: String = "title"
){
   object Headlines:  NavScreen(
      route="headlinesList",
      icon = Icons.Outlined.Article,
      title = "Schlagzeilen"
   )
   object Articles:  NavScreen(
      route = "articlesList",
      icon = Icons.Outlined.Search,
      title ="Suche Artikel"
   )

   object SavedArticles:  NavScreen(
      route = "savedList",
      icon = Icons.Outlined.SavedSearch,
      title ="Gespeichert"
   )

   object Article:  NavScreen(
      route = "article",
      icon = Icons.Outlined.Article,
      title ="Artikel anzeigen"
   )


}


/*
sealed class NavScreen(var route: String, var icon: Int, var title: String) {
   object Home : NavScreen("home", R.drawable.ic_home, "Home")
   object Music : NavScreen("music", R.drawable.ic_music, "Music")
   object Movies : NavScreen("movies", R.drawable.ic_movie, "Movies")
   object Books : NavScreen("books", R.drawable.ic_book, "Books")
   object Profile : NavScreen("profile", R.drawable.ic_profile, "Profile")
}
 */