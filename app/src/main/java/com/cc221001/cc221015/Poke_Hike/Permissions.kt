// PermissionHandler.kt

package com.cc221001.cc221015.Poke_Hike

import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

object Permissions {
    fun requestLocationPermission(activity: ComponentActivity, onPermissionGranted: () -> Unit) {
        val requestPermission =
            activity.registerForActivityResult(ActivityResultContracts.RequestPermission()) {
                if (it) {
                    onPermissionGranted()
                }
            }

        activity.lifecycleScope.launch {
            requestPermission.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }
}
