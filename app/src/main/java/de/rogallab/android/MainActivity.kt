package de.rogallab.android

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.tooling.preview.Preview
import dagger.hilt.android.AndroidEntryPoint
import de.rogallab.android.ui.base.BaseComponentActivity
import de.rogallab.android.ui.navigation.AppNavHost
import de.rogallab.android.ui.news.NewsViewModel
import de.rogallab.android.ui.theme.AppTheme

@AndroidEntryPoint
class MainActivity : BaseComponentActivity(tag) {

   @ExperimentalComposeUiApi
   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)

      setContent {
         AppTheme {
            // A surface container using the 'background' color from the theme
            Surface(color = MaterialTheme.colors.background) {
               AppNavHost()
            }
         }
      }
   }

   companion object {//12345678901234567890123
      const val tag = "ok>MainActivity        "
   }

}

