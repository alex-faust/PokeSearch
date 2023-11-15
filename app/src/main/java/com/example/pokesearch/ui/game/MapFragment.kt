package com.example.pokesearch.ui.game

import android.Manifest
import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.pokesearch.GeofenceBroadcastReceiver
import com.example.pokesearch.R
import com.example.pokesearch.databinding.MapFragmentBinding
import com.example.pokesearch.utils.GeofencingConstants
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingClient
import com.google.android.gms.location.GeofencingRequest
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PointOfInterest
import com.google.android.material.snackbar.Snackbar
import timber.log.Timber
import java.util.Locale
import java.util.Random

class MapFragment: Fragment(), OnMapReadyCallback {

    private lateinit var map: GoogleMap
    private lateinit var binding: MapFragmentBinding
    private val mapViewModel by viewModels<GeofenceViewModel>()
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var thisContext: Context
    private var locationSettingsEnabled = false
    private lateinit var locationCallback: LocationCallback
    private lateinit var geofencingClient: GeofencingClient

    private val geofencePendingIntent: PendingIntent by lazy {
        val intent = Intent(requireContext(), GeofenceBroadcastReceiver::class.java)
        intent.action = ACTION_GEOFENCE_EVENT
        var intentFlagType = PendingIntent.FLAG_UPDATE_CURRENT
        isSupportsOreo {
            intentFlagType = PendingIntent.FLAG_MUTABLE
        }
        PendingIntent.getBroadcast(thisContext, 0, intent, intentFlagType)
    }


    @RequiresApi(Build.VERSION_CODES.Q)
    @SuppressLint("MissingPermission")
    private val requestPermissions = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
        permissions.forEach { actionMap ->
            when (actionMap.key) {
                Manifest.permission.ACCESS_FINE_LOCATION -> {
                    if (actionMap.value) {
                        Timber.i("FINE permission granted")
                        startLocationUpdates()
                    } else {
                        !ActivityCompat.shouldShowRequestPermissionRationale(
                            requireActivity(),
                            Manifest.permission.ACCESS_FINE_LOCATION
                        )
                        Timber.i("FINE permission denied")
                        Toast.makeText(thisContext,
                            "In order to use location, you need to enable it in the app settings.",
                            Toast.LENGTH_LONG).show()
                    }
                }
                Manifest.permission.ACCESS_BACKGROUND_LOCATION -> {
                    if (actionMap.value) {
                        Timber.i("BACKGROUND permission granted")
                    } else {
                        // if permission denied then check whether never
                        // ask again is selected or not by making use of
                        !ActivityCompat.shouldShowRequestPermissionRationale(
                            requireActivity(),
                            Manifest.permission.ACCESS_BACKGROUND_LOCATION
                        )
                        Timber.i("BACKGROUND permission denied")
                    }
                }
            }
        }

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val layoutId = R.layout.map_fragment
        binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.mapViewModel = mapViewModel

        geofencingClient = LocationServices.getGeofencingClient(requireActivity())

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        setDisplayHomeAsUpEnabled(true)

        return binding.root
    }
    @SuppressLint("MissingPermission")
    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        startLocationUpdates()

        Timber.i("onMapReady")
        checkPermissions()

        setPoiClick(map)
        setMapLongClick(map)
        setMapStyle(map)

        fusedLocationProviderClient.lastLocation.addOnSuccessListener {
            if (it == null) {
                startLocationUpdates()
            } else {
                map.moveCamera(
                    CameraUpdateFactory.newLatLngZoom(
                        LatLng(it.latitude, it.longitude), 15f
                    )
                )
            }
            Timber.i("fused location success")
        }
        fusedLocationProviderClient.lastLocation.addOnFailureListener {
            Timber.i("fused location failure")
            Toast.makeText(thisContext,
                "In order to use location, you need to enable it in the app settings.",
                Toast.LENGTH_LONG).show()
        }

    }

    //map stuff

    private fun onLocationSelected(poi: PointOfInterest) {

        Timber.i( "Location selected")

    }

    private fun setPoiClick(map: GoogleMap) {
        map.clear()
        map.setOnPoiClickListener { poi ->
            val poiMarker = map.addMarker(
                MarkerOptions()
                    .position(poi.latLng)
                    .title(poi.name)
            )
            poiMarker?.showInfoWindow()
            //onLocationSelected(poi)
        }
    }
    private fun setMapStyle(map: GoogleMap) {
        /*try {
            val success = map.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                    requireContext(), R.raw.map_style
                )
            )
            if (!success) {
                Log.e("TAG", "Style parsing failed")
            }
        } catch (e: Resources.NotFoundException) {
            Log.e("TAG", "Can't find style. Error: ", e)
        }*/
    }

    private fun setMapLongClick(map: GoogleMap) {
        map.clear()
        map.setOnMapLongClickListener { latLng ->
            val snippet = String.format(
                Locale.getDefault(),
                "Lat: %1$.5f, Long: %2$.5f",
                latLng.latitude,
                latLng.longitude
            )
            map.addMarker(
                MarkerOptions()
                    .position(latLng)
                    .title(getString(R.string.dropped_pin))
                    .snippet(snippet)
            )
            onLocationSelected(PointOfInterest(latLng, getString(R.string.dropped_pin), snippet))
        }

        //TODO maybe on long click, can set user's location to that point
    }



    //location stuff
    @RequiresApi(Build.VERSION_CODES.Q)
    private fun checkDeviceLocationSettingsAndStartGeofence(resolve: Boolean = true) {
        val timeInterval: Long = 5000
        val locationRequest = LocationRequest
            .Builder(Priority.PRIORITY_LOW_POWER, timeInterval).build()
        val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
        val settingsClient = LocationServices.getSettingsClient(thisContext)
        val locationSettingsResponseTask = settingsClient.checkLocationSettings(builder.build())

        locationSettingsResponseTask.addOnFailureListener { exception ->
            if (exception is ResolvableApiException && resolve) {
                try {
                    startIntentSenderForResult(
                        exception.resolution.intentSender,
                        REQUEST_CODE_DEVICE_LOCATION_SETTINGS,
                        null,
                        0,
                        0,
                        0,
                        null
                    )
                } catch (sendEx: IntentSender.SendIntentException) {
                    Timber.i("Error getting location settings resolution: $sendEx.message")
                    locationSettingsEnabled = false
                }
            } else {
                Snackbar.make(
                    binding.root,
                    R.string.location_required_error, Snackbar.LENGTH_INDEFINITE
                ).setAction(android.R.string.ok) {
                    checkDeviceLocationSettingsAndStartGeofence()
                }.show()
            }
        }
        locationSettingsResponseTask.addOnCompleteListener {
            if (it.isSuccessful) {
                locationSettingsEnabled = true
                Timber.i("location settings response complete, locationSettings set to true? $locationSettingsEnabled")
                addGeofenceForPokemon()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    @SuppressLint("MissingPermission")
    private fun addGeofenceForPokemon() {
        if (mapViewModel.geofenceIsActive()) return //first check if you have any current geofences, if so, don't add an other
        val currentGeofenceIndex = mapViewModel.nextGeofenceIndex() //find the current geofence index
        if(currentGeofenceIndex >= GeofencingConstants.NUM_LANDMARKS) {
            removeGeofences()
            mapViewModel.geofenceActivated()
            return
        }

        val random = Random(System.currentTimeMillis())
        val index = random.nextInt(GeofencingConstants.SF_LANDMARK_DATA.size-1)
        val currentGeofenceData = GeofencingConstants.SF_LANDMARK_DATA[index]

        binding.hintText.setText(GeofencingConstants.SF_LANDMARK_DATA[index].hint)

        val geofence = Geofence.Builder()
            .setRequestId(currentGeofenceData.id)
            .setCircularRegion(currentGeofenceData.latLong.latitude,
                currentGeofenceData.latLong.longitude,
                GeofencingConstants.GEOFENCE_RADIUS_IN_METERS
            )
            .setExpirationDuration(GeofencingConstants.GEOFENCE_EXPIRATION_IN_MILLISECONDS)
            .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER)
            .build()

        val geofencingRequest = GeofencingRequest.Builder()
            .setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
            .addGeofence(geofence)
            .build()

        geofencingClient.removeGeofences(geofencePendingIntent).run {
            addOnCompleteListener {
                geofencingClient.addGeofences(geofencingRequest, geofencePendingIntent).run {
                    addOnSuccessListener {
                        Toast.makeText(requireContext(), R.string.geofences_added,
                            Toast.LENGTH_SHORT)
                            .show()
                        Timber.i("Add Geofence ${geofence.requestId}")
                        mapViewModel.geofenceActivated()
                    }
                    addOnFailureListener {
                        Toast.makeText(requireActivity(), R.string.geofences_not_added,
                            Toast.LENGTH_SHORT).show()
                        if ((it.message != null)) {
                            Timber.i("$it.message!!")
                        }
                    }
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun removeGeofences() {
        if (!isBackgroundPermissionGranted()) {
            return
        }
        geofencingClient.removeGeofences(geofencePendingIntent).run {
            addOnSuccessListener {
                Timber.i(getString(R.string.geofences_removed))
                Toast.makeText(requireContext(), R.string.geofences_removed, Toast.LENGTH_SHORT)
                    .show()
            }
            addOnFailureListener {
                Timber.i(getString(R.string.geofences_not_removed))
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        Timber.i("Starting location updates")

        val locationRequest = LocationRequest.create().apply {
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            interval = 5000L
        }
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                for (location in locationResult.locations) {
                    map.moveCamera(
                        CameraUpdateFactory.newLatLngZoom(
                            LatLng(location.latitude, location.longitude), 15f
                        )
                    )
                }
            }
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest,
            locationCallback, Looper.getMainLooper())
    }
    private fun stopLocationUpdates() {
        Timber.i("Stopping location updates")
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }

    //permission stuff
    @SuppressLint("MissingPermission")
    @RequiresApi(Build.VERSION_CODES.Q)
    private fun checkPermissions() {
        if (mapViewModel.geofenceIsActive()) {
            Timber.i("geofence is active")
            return
        }
        Timber.i("checking permissions")

        if (isForegroundPermissionsGranted() && isBackgroundPermissionGranted()) {
            Timber.i("both permissions are granted")

            map.isMyLocationEnabled = true
            Timber.i("my location enabled is true")

            checkDeviceLocationSettingsAndStartGeofence()
        } else {
            isSupportsQ {
                Timber.i("requesting both permissions")

                requestPermissions.launch(
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_BACKGROUND_LOCATION
                    )
                )
            }
            Timber.i("requesting fine permission")

            requestPermissions.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            )
        }
    }

    private fun isForegroundPermissionsGranted(): Boolean {
        return PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(
            thisContext,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    }
    @RequiresApi(Build.VERSION_CODES.Q)
    private fun isBackgroundPermissionGranted(): Boolean {
        return PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(
            thisContext,
            Manifest.permission.ACCESS_BACKGROUND_LOCATION
        )
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        thisContext = context
    }
    private fun isSupportsQ(f: () -> Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            f()
        }
    }

    private fun isSupportsOreo(f: () -> Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            f()
        }
    }

    private fun Fragment.setDisplayHomeAsUpEnabled(bool: Boolean) {
        if (activity is AppCompatActivity) {
            (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(
                bool
            )
        }
    }
    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onResume() {
        super.onResume()
        if (!isForegroundPermissionsGranted() && !locationSettingsEnabled) {
            Timber.i("foreground: ${isForegroundPermissionsGranted()} and locationSettingsEnabled is: $locationSettingsEnabled")
            startLocationUpdates()
        }
    }



    override fun onPause() {
        super.onPause()
        stopLocationUpdates()
    }

    companion object {
        private const val REQUEST_CODE_DEVICE_LOCATION_SETTINGS = 20
        internal const val ACTION_GEOFENCE_EVENT = "ACTION_GEOFENCE_EVENT"
    }




}