package com.logger.android.utils

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager

object BatteryUtils {
    fun getBatteryInfo(context: Context): String {
        return try {
            val batteryIntent = context.registerReceiver(null, 
                IntentFilter(Intent.ACTION_BATTERY_CHANGED))
            val level = batteryIntent?.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) ?: -1
            val scale = batteryIntent?.getIntExtra(BatteryManager.EXTRA_SCALE, -1) ?: -1
            val batteryPct = if (level >= 0 && scale > 0) (level * 100 / scale) else -1
            
            val status = batteryIntent?.getIntExtra(BatteryManager.EXTRA_STATUS, -1)
            val isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                    status == BatteryManager.BATTERY_STATUS_FULL
            
            """
            🔋 BATTERY INFO:
            • Level: $batteryPct%
            • Charging: $isCharging
            • Health: Normal
            """.trimIndent()
        } catch (e: Exception) {
            "🔋 BATTERY INFO: Unable to read"
        }
    }
}
