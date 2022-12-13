package de.rogallab.android.domain.utilities

import android.util.Log
import de.rogallab.android.domain.ILogger

object LoggerImplCat: ILogger {
   override fun v(tag: String, message: String) { Log.d(tag, format(message)) }
   override fun d(tag: String, message: String) { Log.d(tag, formatMessage(message)) }
   override fun i(tag: String, message: String) { Log.i(tag, formatMessage(message)) }
   override fun e(tag: String, message: String) { Log.e(tag, formatMessage(message)) }

   private fun formatMessage(message:String) =
      String.format("%-50s %s", message, Thread.currentThread().toString())
   private fun format(message:String) =
      String.format("%s", message)
}

object LoggerImplPrint: ILogger {
   override fun v(tag: String, message: String) { println(format(tag,message)) }
   override fun e(tag: String, message: String) { println(formatMessage(tag,message)) }
   override fun d(tag: String, message: String) { println(formatMessage(tag,message)) }
   override fun i(tag: String, message: String) { println(formatMessage(tag,message)) }

   private fun formatMessage(tag: String, message:String) =
      String.format("%-23s:%-50s %s", tag, message, Thread.currentThread().toString())
   private fun format(tag: String, message:String) =
      String.format("%-23s:%s ", tag, message)
}