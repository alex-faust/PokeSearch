package com.example.pokesearch.utils

import android.content.Context
import com.example.pokesearch.R
import com.google.android.gms.location.GeofenceStatusCodes
import com.google.android.gms.maps.model.LatLng
import java.util.concurrent.TimeUnit

fun errorMessage(context: Context, errorCode: Int): String {
    val resources = context.resources
    return when (errorCode) {
        GeofenceStatusCodes.GEOFENCE_NOT_AVAILABLE -> resources.getString(
            R.string.geofence_not_available
        )
        GeofenceStatusCodes.GEOFENCE_TOO_MANY_GEOFENCES -> resources.getString(
            R.string.geofence_too_many_geofences
        )
        GeofenceStatusCodes.GEOFENCE_TOO_MANY_PENDING_INTENTS -> resources.getString(
            R.string.geofence_too_many_pending_intents
        )
        else -> resources.getString(R.string.unknown_geofence_error)
    }
}
data class SFLandmarkDataObject(val id:  String, val hint: Int, val name: Int, val latLong: LatLng)
internal object GeofencingConstants {

    val  GEOFENCE_EXPIRATION_IN_MILLISECONDS: Long = TimeUnit.HOURS.toMillis(1)

    val SF_LANDMARK_DATA = arrayOf(
        SFLandmarkDataObject(
            "golden_gate_bridge",
            R.string.hint_golden_gate_bridge,
            R.string.found_golden_gate_bridge,
            LatLng(37.8200, -122.4783)),
        SFLandmarkDataObject(
            "haight_ashbury",
            R.string.hint_haight_ashbury,
            R.string.found_haight_ashbury,
            LatLng(37.7700,-122.4469)),
        SFLandmarkDataObject(
            "fishermans_wharf",
            R.string.hint_fishermans_wharf,
            R.string.found_fishermans_wharf,
            LatLng(37.8086,-122.4126)),
        SFLandmarkDataObject(
            "dolores_Park",
            R.string.hint_dolores_park,
            R.string.found_dolores_park,
            LatLng(37.7598, -122.4272))
    )


    val NUM_LANDMARKS = SF_LANDMARK_DATA.size
    const val GEOFENCE_RADIUS_IN_METERS = 100f
    const val EXTRA_GEOFENCE_INDEX = "GEOFENCE_INDEX"
}