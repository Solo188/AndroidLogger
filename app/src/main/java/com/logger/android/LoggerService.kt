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

class LoggerService : Service() {
    
    override fun onBind(intent: Intent?): IBinder? = null
    
    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        startForeground(1, createNotification())
    }
    
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // Простая реализация для теста
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
    
    override fun onDestroy() {
        super.onDestroy()
    }
}
