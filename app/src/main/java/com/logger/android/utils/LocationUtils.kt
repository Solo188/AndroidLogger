package com.logger.android.utils

import android.content.Context
import android.location.LocationManager

object LocationUtils {
    fun getLocationInfo(context: Context): String {
        return try {
            val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            val gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
            val networkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
            
            """
            📍 LOCATION INFO:
            • GPS Enabled: $gpsEnabled
            • Network Location: $networkEnabled
            • Status: ${if (gpsEnabled || networkEnabled) "Available" else "Disabled"}
            """.trimIndent()
        } catch (e: Exception) {
            "📍 LOCATION INFO: Unable to read"
        }
    }
}
