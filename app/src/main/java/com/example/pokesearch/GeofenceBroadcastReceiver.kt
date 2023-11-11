package com.example.pokesearch

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import com.example.pokesearch.ui.game.GameFragment.Companion.ACTION_GEOFENCE_EVENT
import com.example.pokesearch.utils.GeofencingConstants
import com.example.pokesearch.utils.errorMessage
import com.example.pokesearch.utils.sendGeofenceEnteredNotification
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingEvent
import timber.log.Timber

class GeofenceBroadcastReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == ACTION_GEOFENCE_EVENT) {
            val geofencingEvent = GeofencingEvent.fromIntent(intent)

            if (geofencingEvent != null) {
                if (geofencingEvent.hasError()) {
                    val errorMessage = errorMessage(context, geofencingEvent.errorCode)
                    Timber.e(errorMessage)
                    return
                }
            }
            if (geofencingEvent != null) {
                if (geofencingEvent.geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER) {
                    Timber.tag(TAG).e(context.getString(R.string.geofence_entered))

                    val fenceId = when {
                        geofencingEvent.triggeringGeofences?.isNotEmpty() == true ->
                            geofencingEvent.triggeringGeofences?.get(0)?.requestId

                        else -> {
                            Timber.tag(TAG).e("No Geofence Trigger Found! Abort mission!")
                            return
                        }
                    }
                    // Check geofence against the constants listed in GeofenceUtil.kt to see if the
                    // user has entered any of the locations we track for geofences.
                    val foundIndex = GeofencingConstants.SF_LANDMARK_DATA.indexOfFirst {
                        it.id == fenceId
                    }

                    // Unknown Geofences aren't helpful to us
                    if ( -1 == foundIndex ) {
                        Timber.tag(TAG).e("Unknown Geofence: Abort Mission")
                        return
                    }

                    val notificationManager = ContextCompat.getSystemService(
                        context,
                        NotificationManager::class.java
                    ) as NotificationManager

                    Timber.i("Geofence entered")
                    notificationManager.sendGeofenceEnteredNotification(context, foundIndex)
                }
            }
        }
    }
}

private const val TAG = "Pokemon Geofence"
