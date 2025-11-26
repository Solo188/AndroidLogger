package com.logger.android.utils

object MemoryUtils {
    fun getMemoryInfo(context: android.content.Context): String {
        return try {
            val runtime = Runtime.getRuntime()
            val usedMemory = (runtime.totalMemory() - runtime.freeMemory()) / (1024 * 1024)
            val totalMemory = runtime.totalMemory() / (1024 * 1024)
            val maxMemory = runtime.maxMemory() / (1024 * 1024)
            
            """
            💾 MEMORY INFO:
            • Used: ${usedMemory}MB
            • Total: ${totalMemory}MB
            • Max: ${maxMemory}MB
            """.trimIndent()
        } catch (e: Exception) {
            "💾 MEMORY INFO: Unable to read"
        }
    }
}
