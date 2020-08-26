package com.assignment.fluper.utils

import android.util.Log
import com.assignment.fluper.BuildConfig
import java.io.PrintWriter
import java.io.StringWriter

/**
 *  @AppLogger This class helps you disable the Log in the release build. The boolean value of
 *  ON_DEBUG_TRUE is maintained in the gradle, if we select release variant, it will
 *  change to false.
 *
 *  @author  Sushant Kumar
 */
object AppLogger {
    fun e(tag: String?, msg: String) {
        if (!BuildConfig.ON_DEBUG_MODE) return
        if (msg.length > 4000) {
            Log.e(tag, msg.substring(0, 4000))
            e("", "" + checkInstanceOfException(msg.substring(4000)))
        } else Log.e(tag, "" + checkInstanceOfException(msg))
    }

    private fun checkInstanceOfException(msg: Any): Any {
        var msg = msg
        try {
            if (msg is Exception) {
                val errors = StringWriter()
                msg.printStackTrace(PrintWriter(errors))
                msg = errors.toString()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return msg
    }
}