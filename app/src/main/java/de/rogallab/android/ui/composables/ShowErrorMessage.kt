package de.rogallab.android.ui.composables

import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import de.rogallab.android.domain.utilities.LogFun

@Composable
fun ShowErrorMessage(
   scaffoldState: ScaffoldState, // State ↓
   errorMessage: String?,        // State ↓
   actionLabel: String?,         // State ↓
   onErrorDismiss: () -> Unit,   // Event ↑
   onErrorAction: () -> Unit     // Event ↑
) {
   val tag: String = "ok>ShowErrorMessage   ."

   // Show snackbar using a coroutine, when the coroutine is cancelled the
   // snackbar will automatically dismiss. This coroutine will cancel whenever
   // uiState.Error` is false, and only start when `state.hasError` is true
   // (due to the above if-check), or if `scaffoldState.snackbarHostState` changes.
   errorMessage?.let { message: String ->
      LaunchedEffect(scaffoldState.snackbarHostState) { // this:CoroutineScope
         LogFun(tag, "launch Snackbar with ErrorMessage: $message")
         val snackbarResult = scaffoldState.snackbarHostState.showSnackbar(
            message = message,
            actionLabel = actionLabel,
            duration = SnackbarDuration.Indefinite
         )
         when (snackbarResult) {
            SnackbarResult.Dismissed -> {
               LogFun(tag, "SnackbarResult.Dismissed")
               onErrorDismiss()
            }
            SnackbarResult.ActionPerformed -> {
               LogFun(tag, "SnackbarResult.ActionPerformed")
               onErrorAction()
            }
         }
      }
   }
}