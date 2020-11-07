package com.example.focusstarthomework.utils

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.PowerManager
import android.provider.Settings
import android.widget.Toast
import com.example.focusstarthomework.R

/**
 * return false if in settings "Not optimized" and true if "Optimizing battery use"
 */
private fun isBatteryOptimized(context: Context): Boolean {
    val powerManager = context.getSystemService(Context.POWER_SERVICE) as PowerManager
    val name = context.packageName
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        return !powerManager.isIgnoringBatteryOptimizations(name)
    }
    return false
}

fun checkBattery(context: Context) {
    if (isBatteryOptimized(context) && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
        val name = context.resources.getString(R.string.app_name)
        Toast.makeText(context, "Battery optimization -> All apps -> $name -> Don't optimize", Toast.LENGTH_LONG).show()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val intent = Intent(Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS)
            context.startActivity(intent)
        }

    }
}