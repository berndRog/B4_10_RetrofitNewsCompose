package de.rogallab.android.ui.base

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import de.rogallab.android.domain.ILogger
import javax.inject.Inject

open class BaseComponentActivity(private val _tag: String) : ComponentActivity() {

   @Inject
   lateinit var logger: ILogger

   // Start of full lifetime
   // Activity is first created
   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      if (savedInstanceState == null)
         logger.d(_tag, "onCreate() Bundle == null")
      else
         logger.d(_tag, "onCreate() Bundle != null")
   }

   // Activity is restarted
   override fun onRestart() {
      super.onRestart()
      logger.d(_tag, "onRestart()")
   }

   // Start of visible lifetime
   // Activity is visible
   override fun onStart() {
      super.onStart()
      logger.d(_tag, "onStart()")
   }

   // Start of the active lifetime
   // Activity interacts with the user
   override fun onResume() {
      super.onResume()
      logger.d(_tag, "onResume()")
   }

   // End of active lifetime
   // User is leaving activity
   override fun onPause() {
      logger.d(_tag, "onPause()")
      super.onPause()
   }

   // End of visible lifetime
   // Activity is no longer visible
   override fun onStop() {
      logger.d(_tag, "onStop()")
      super.onStop()
   }

   // End of full lifetime
   // Called before the activity is destroyed.
   override fun onDestroy() {
      logger.d(_tag, "onDestroy()")
      super.onDestroy()
   }

   // override save/restore state methods -------------------------------------
   // Save instance state: invoked when the activity may be temporarily destroyed,
   override fun onSaveInstanceState(savedStateBundle: Bundle) {
      super.onSaveInstanceState(savedStateBundle)
      logger.d(_tag, "onSaveInstanceState()")
   }

   override fun onRestoreInstanceState(savedInstanceState: Bundle) {
      super.onRestoreInstanceState(savedInstanceState)
      logger.d(_tag, "onRestoreInstanceState()")
   }

   override fun onConfigurationChanged(newConfig: Configuration) {
      super.onConfigurationChanged(newConfig)

      // Checks the orientation of the screen
      if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
         logger.d(_tag, "landscape")
      } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
         logger.d(_tag, "portrait")
      }
   }

   override fun onWindowFocusChanged(hasFocus: Boolean) {
      logger.d(_tag, "onWindowFocusChanged() $hasFocus")
      super.onWindowFocusChanged(hasFocus)
   }

   override fun onTrimMemory(level: Int) {
      super.onTrimMemory(level)
      logger.d(_tag, "onTrimMemory() $level")
      if (level == TRIM_MEMORY_RUNNING_MODERATE) logger.d(_tag, "TRIM_MEMORY_RUNNING_MODERATE")
      if (level == TRIM_MEMORY_RUNNING_LOW) logger.d(_tag, "TRIM_MEMORY_RUNNING_LOW")
      if (level == TRIM_MEMORY_RUNNING_CRITICAL) logger.d(_tag, "TRIM_MEMORY_RUNNING_CRITICAL")
      if (level == TRIM_MEMORY_BACKGROUND) logger.d(_tag, "TRIM_MEMORY_BACKGROUND")
      if (level == TRIM_MEMORY_COMPLETE) logger.d(_tag, "TRIM_MEMORY_COMPLETE")
      if (level == TRIM_MEMORY_UI_HIDDEN) logger.d(_tag, "TRIM_MEMORY_UI_HIDDEN")
      if (level == TRIM_MEMORY_MODERATE) logger.d(_tag, "TRIM_MEMORY_MODERATE")
   }
}