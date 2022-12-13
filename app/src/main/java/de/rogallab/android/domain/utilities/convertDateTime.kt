package de.rogallab.android.domain.utilities

import java.time.*
import java.time.ZoneOffset.UTC
import java.time.format.DateTimeFormatter

/*
   Zulu Time (Coordinated Universal Time) == UTC(GMT) +0
   UTC	2022-06-27T13:22:58Z
   Zoned DateTime(germany)
   Timezone  (Europe/berlin)
*/


fun toZuluDtString(date: LocalDate): String? =
   try {
      val dtf = DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm:ssX")
      date.atStartOfDay().atOffset(UTC)
          .format(dtf)
   } catch (e: Exception) {
      null
   }

fun toZuluDtString(dateTime: LocalDateTime): String? =
   try {
      val dtf = DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm:ssX")
      // convert to UTC Time Zone
      val zdt = dateTime.atZone(ZoneId.systemDefault())
      zdt.withZoneSameInstant(ZoneId.of("+0"))
         .format(dtf)
   } catch (e: Exception) {
      null
   }

fun toLocalDate(zuluDtString: String): LocalDate? =
   try {
      val dtf = DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm:ssX")
      LocalDate.parse(zuluDtString, dtf)
   } catch (e: Exception) {
      null
   }

fun toLocalDateTime(zuluDtString: String): LocalDateTime? =
   try {
      val dtf = DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm:ssX")
//    val dtf = DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm")

      val zoneId = ZoneId.systemDefault()
      var odt = OffsetDateTime.now()
      var zoneOffset = odt.offset

      val zdt = ZonedDateTime
         .parse(zuluDtString, dtf)
         .withZoneSameInstant(ZoneId.systemDefault())
      zdt.toLocalDateTime()
   } catch (e: Exception) {
      null
   }

fun toDateString(localDate:LocalDate): String? =
   try {
      val dtf = DateTimeFormatter.ofPattern ("d.M.yyyy")
      dtf.format(localDate)
   } catch (e: Exception) {
      null
   }

fun toDateString(localDateTime:LocalDateTime): String? =
   try {
      val dtf = DateTimeFormatter.ofPattern ("d.M.yyyy")
      dtf.format(localDateTime)
   } catch (e: Exception) {
      null
   }

fun toDateTimeString(localDate:LocalDate): String? =
   try {
      toDateTimeString(localDate.atStartOfDay())
   } catch (e: Exception) {
      null
   }

fun toDateTimeString(localDateTime:LocalDateTime): String? =
   try {
      val formatter = DateTimeFormatter.ofPattern ("d.M.yyyy hh:mm")
      val result = localDateTime.format(formatter)
      result
   } catch (e: Exception) {
      null
   }

fun toEpoch(zuluDtString: String): Long? =
   try {
      Instant.parse(zuluDtString).toEpochMilli()
   } catch (e: Exception) {
      null
   }


fun toLocalDateFromToken(stringToken: String): LocalDate? =

   try {
      var token = stringToken.toInt()
      val day = token / 1_000_000
      token -= day * 1_000_000
      val month = token / 10_000
      val year = token - month * 10_000
      LocalDate.of(year, month, day)
   }
   catch (e: Exception) {
      null
   }
