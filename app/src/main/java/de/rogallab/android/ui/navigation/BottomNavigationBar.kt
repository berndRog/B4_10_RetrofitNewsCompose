package de.rogallab.android.ui.comp

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import de.rogallab.android.domain.utilities.LogFun
import de.rogallab.android.ui.navigation.NavScreen

@Composable
fun BottomNavigationBar(
   navController: NavController
) {
   val items = listOf(
      NavScreen.Headlines,      // Headlines list
      NavScreen.Articles,       // Articles lsit for a search text
      NavScreen.SavedArticles   // saved Article list
   )
   BottomNavigation(
      backgroundColor = MaterialTheme.colors.primary,
      contentColor = MaterialTheme.colors.onSecondary
   ) {
               //12345678901234567890123
      val tag = "ok>BottomNavigationBar "

      val navBackStackEntry by navController.currentBackStackEntryAsState()
      val currentRoute = navBackStackEntry?.destination?.route

      items.forEach { item ->

         BottomNavigationItem(
            icon = { Icon(imageVector = item.icon, contentDescription = item.title) },
            label = { Text(text = item.title) },
            selectedContentColor = Color.White,
            //unselectedContentColor = Color.Black.copy(0.6f),
            alwaysShowLabel = true,
            selected = currentRoute == item.route,
            onClick = {
               LogFun(tag,"navigateTo ${item.route}")
               navController.navigate(item.route) {
                  navController.graph.startDestinationRoute?.let { route ->
                     popUpTo(route) { saveState = true  }
                  }
                  // Avoid multiple copies of the same destination when
                  // reselecting the same item
                  launchSingleTop = true
                  // Restore state when reselecting a previously selected item
                  restoreState = true
               }
            }
         )
      }
      /*
            onClick = { selectedItem: NavScreen ->
               logThread("==>navigateTo....", "${selectedItem.route}")
               navController.navigate(selectedItem.route) {
                  // Pop up to the start destination of the graph to
                  // avoid building up a large stack of destinations
                  // on the back stack as users select items
                  popUpTo(navController.graph.findStartDestination().id) { saveState = true  }
                  // Avoid multiple copies of the same destination when
                  // reselecting the same item
                  launchSingleTop = true
                  // Restore state when reselecting a previously selected item
                  restoreState = true
               }
            }
            */

   }
}