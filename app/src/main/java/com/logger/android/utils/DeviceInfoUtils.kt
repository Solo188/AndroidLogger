package com.logger.android.utils

import android.os.Build

object DeviceInfoUtils {
    fun getDeviceInfo(context: android.content.Context): String {
        return """
            📟 DEVICE INFO:
            • Model: ${Build.MODEL}
            • Manufacturer: ${Build.MANUFACTURER}
            • Android: ${Build.VERSION.RELEASE} (SDK ${Build.VERSION.SDK_INT})
            • Product: ${Build.PRODUCT}
            • Device: ${Build.DEVICE}
        """.trimIndent()
    }
}
