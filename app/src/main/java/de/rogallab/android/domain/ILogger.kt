package de.rogallab.android.domain

interface ILogger {
   fun v(tag: String, message: String)
   fun d(tag: String, message: String)
   fun i(tag: String, message: String)
   fun e(tag: String, message: String)
}