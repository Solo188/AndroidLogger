package com.logger.android

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat

class MainActivity : AppCompatActivity() {
    
    private val PERMISSIONS = arrayOf(
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.ACCESS_FINE_LOCATION
    )
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        val startBtn: Button = findViewById(R.id.start_button)
        startBtn.setOnClickListener {
            if (checkPermissions()) {
                startLoggerService()
            } else {
                requestPermissions()
            }
        }
    }
    
    private fun checkPermissions(): Boolean {
        return PERMISSIONS.all { permission ->
            ActivityCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED
        }
    }
    
    private fun requestPermissions() {
        ActivityCompat.requestPermissions(this, PERMISSIONS, 100)
    }
    
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 100 && grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
            startLoggerService()
        } else {
            Toast.makeText(this, "Permissions denied!", Toast.LENGTH_SHORT).show()
        }
    }
    
    private fun startLoggerService() {
        val intent = Intent(this, LoggerService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(intent)
        } else {
            startService(intent)
        }
        Toast.makeText(this, "Logger service started", Toast.LENGTH_SHORT).show()
    }
}
