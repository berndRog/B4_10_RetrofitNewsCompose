package de.rogallab.android.data.database
import androidx.room.TypeConverter
import de.rogallab.android.domain.dtos.Source

public class Converters {

   @TypeConverter
   fun fromSource(source: Source): String {
      return source?.name ?: ""
   }

   @TypeConverter
   fun toSource(name: String?): Source {
      var source = Source("","")
      name?.let {
         source = Source(name, name)
      }
      return source
   }
}