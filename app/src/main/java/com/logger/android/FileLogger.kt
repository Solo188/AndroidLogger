package com.logger.android

import android.content.Context
import android.os.Environment
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

object FileLogger {
    fun writeLog(context: Context, logData: String) {
        try {
            val logFile = getLogFile(context)
            FileOutputStream(logFile, true).use { output ->
                output.write("$logData\n\n".toByteArray())
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    
    private fun getLogFile(context: Context): File {
        val downloadsDir = context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)
        val appDir = File(downloadsDir, "AndroidLogger")
        if (!appDir.exists()) {
            appDir.mkdirs()
        }
        return File(appDir, "device_logs.txt")
    }
}
