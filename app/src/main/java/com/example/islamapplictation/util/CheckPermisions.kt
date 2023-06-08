package com.example.islamapplictation.util

import android.Manifest
import android.app.Activity
import android.content.Context
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

class CheckPermisions (val activity: Activity ) {
  private  val REQUEST_CODE = 100
     val  permissionArray:Array<String> = arrayOf(
        Manifest.permission.INTERNET,
        Manifest.permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS,
        Manifest.permission.POST_NOTIFICATIONS,
         Manifest.permission.ACCESS_NETWORK_STATE

    )

        //   <uses-permission android:name="android.permission.INTERNET" />
//    <uses-permission android:name="android.permission.POST_NOTIFICATIONS" />
//    <uses-permission android:name="android.permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS" />
    fun checkPermission() {
            if (activity.checkSelfPermission(Manifest.permission.INTERNET) != android.content.pm.PackageManager.PERMISSION_GRANTED
                || activity.checkSelfPermission(Manifest.permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS) != android.content.pm.PackageManager.PERMISSION_GRANTED
                || activity.checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) != android.content.pm.PackageManager.PERMISSION_GRANTED
            ) {
                Log.d("TAG", "checkPermission: ")
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    activity.requestPermissions(permissionArray,REQUEST_CODE)
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
                            activity.requestPermissions(permissions,REQUEST_CODE)
                        }
                    }
                }
            }
        }

    }