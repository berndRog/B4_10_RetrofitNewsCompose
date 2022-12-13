package de.rogallab.android.ui.news

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import de.rogallab.android.domain.utilities.LogComp
import de.rogallab.android.domain.utilities.toDateTimeString
import de.rogallab.android.domain.utilities.toLocalDateTime
import de.rogallab.android.ui.theme.paddings
import java.time.LocalDateTime

@ExperimentalMaterialApi
@Composable
fun NewsItem(
   title: String?,
   description: String?,
   url: String?,
   imagePath: String?,
   publishedAt: String?,
   onClick: (String) -> Unit
) {
           // 12345678901234567890123
   val tag = "ok>NewsItem            "

   val urlPath by remember { mutableStateOf(url) }


   var dateTimeString = ""

   publishedAt?.let{
      var timeString = it
      //if(! timeString.trim().endsWith(".Z"))   timeString+=".Z"
      val dateTime: LocalDateTime? = toLocalDateTime(timeString)
      dateTimeString = dateTime?.let{ toDateTimeString(dateTime) } ?: ""
   }


   Column(         modifier = Modifier
      .fillMaxWidth()
      .padding(vertical = MaterialTheme.paddings.tiny)
      .clickable { onClick(urlPath ?: "") }
   ) {
      Text(
         text = title ?: "",
         style = MaterialTheme.typography.body1
      )

      Row() {
         imagePath?.let { path: String ->                  // State ↓
            LogComp(tag, "ContactImage $path")
            AsyncImage(
               model = path,
               contentDescription = "Bild des Kontakts",
               modifier = Modifier
                  .width(250.dp)
                  .clip(RoundedCornerShape(percent = 5)),

               alignment = Alignment.CenterStart,
               contentScale = ContentScale.Fit
            )
         }
         Text(
            modifier = Modifier.padding(start=MaterialTheme.paddings.tiny),
            text = dateTimeString,
            style = MaterialTheme.typography.body2
         )
      }
      Text(
         modifier = Modifier.padding(start=MaterialTheme.paddings.tiny),
         text = description ?: "",
         style = MaterialTheme.typography.body2
      )
   }

   Divider(modifier = Modifier
      .padding(bottom = MaterialTheme.paddings.tiny))

}

//
//package de.rogallab.android.ui.news
//
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material.*
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.unit.dp
//import coil.compose.AsyncImage
//import de.rogallab.android.domain.utilities.LogComp
//import de.rogallab.android.domain.utilities.toDateTimeString
//import de.rogallab.android.domain.utilities.toLocalDateTime
//import de.rogallab.android.ui.theme.paddings
//import java.time.LocalDateTime
//
//@ExperimentalMaterialApi
//@Composable
//fun NewsItem(
//   title: String?,
//   description: String?,
//   url: String?,
//   imagePath: String?,
//   name: String?,
//   publishedAt: String?,
//   onClick: (String) -> Unit
//) {
//   // 12345678901234567890123
//   val tag = "ok>NewsItem           ."
//
//   val urlPath by remember { mutableStateOf(url) }
//
//
//   var dateTimeString = ""
//   publishedAt?.let{
//      val dateTime: LocalDateTime? = toLocalDateTime(publishedAt)
//      dateTimeString = dateTime?.let{ toDateTimeString(it) } ?: ""
//   }
//
//   Row(modifier = Modifier
//      .fillMaxWidth()
//      .padding(vertical = MaterialTheme.paddings.tiny)
//      .clickable { onClick(urlPath ?: "") }
//   ) {
//
//      Column(
//         modifier = Modifier.width(150.dp)
//      ) {
//         imagePath?.let { path: String ->                  // State ↓
//            LogComp(tag, "ContactImage $path")
//            AsyncImage(
//               model = path,
//               contentDescription = "Bild des Kontakts",
//               modifier = Modifier
//                  .size(width = 150.dp, height = 100.dp)
//                  .clip(RoundedCornerShape(percent = 5)),
//               alignment = Alignment.Center,
//               contentScale = ContentScale.Crop
//            )
//         }
//         Text(
//            text = name ?: "",
//            style = MaterialTheme.typography.body2
//         )
//
//         Text(
//            text = dateTimeString,
//            style = MaterialTheme.typography.body2
//         )
//      }
//      Column(
//         modifier = Modifier
//            .padding(start = MaterialTheme.paddings.tiny),
//      ) {
//         Text(
//            text = title ?: "",
//            style = MaterialTheme.typography.body1
//         )
//         Text(
//            text = description ?: "",
//            style = MaterialTheme.typography.body2
//         )
//      }
//   }
//
//   Divider(modifier = Modifier
//      .padding(bottom = MaterialTheme.paddings.tiny))
//
//}
