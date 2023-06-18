package com.example.islamapplictation.util

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat.requestPermissions
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.internal.managers.ApplicationComponentManager
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

class CheckPermisions(val activity: Activity) {
    val REQUEST_CODE = 100
     @RequiresApi(Build.VERSION_CODES.TIRAMISU)
     val permissionArray = arrayOf(
        Manifest.permission.INTERNET,
        Manifest.permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS,
        Manifest.permission.POST_NOTIFICATIONS,
        Manifest.permission.ACCESS_NETWORK_STATE,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )

    fun checkPermission() {
        if (activity.checkSelfPermission(Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED
            || activity.checkSelfPermission(Manifest.permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS) != PackageManager.PERMISSION_GRANTED
            || activity.checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED ||
            activity.checkSelfPermission(Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED ||
            activity.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED

        ) {
            Log.d("TAG", "checkPermission: ")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                activity.requestPermissions(permissionArray, REQUEST_CODE)
            }
        }
    }


    fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,

        ) {
        if (requestCode == REQUEST_CODE) {
            if (grantResults.isNotEmpty()) {
                for (i in grantResults.indices) {
                    if (grantResults[i] != android.content.pm.PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(
                            activity.baseContext,
                            "Please Allow this permision please ${permissions[i]} to use this feature",
                            Toast.LENGTH_SHORT
                        ).show()
                        activity.requestPermissions(permissions, REQUEST_CODE)
                    }
                }
            }
        }
    }

}