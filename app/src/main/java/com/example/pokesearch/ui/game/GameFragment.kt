package com.example.pokesearch.ui.game

import android.Manifest
import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.Application
import android.content.IntentSender
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.SavedStateViewModelFactory
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.pokesearch.R
import com.example.pokesearch.databinding.GameFragmentBinding
import com.example.pokesearch.ui.CanvasFrame
import com.example.pokesearch.utils.setQuery
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.Priority
import com.google.android.material.snackbar.Snackbar
import timber.log.Timber

class GameFragment : Fragment() {

    private val TAG = "GameFragment"
    private lateinit var binding: GameFragmentBinding
    private lateinit var viewModel: GameViewModel
    private val runningQorLater = Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q
    private val REQUEST_LOCATION_PERMISSION = 1
    private var searchQuery = " name = "

    //private lateinit var geofencingClient: GeofencingClient

    /*private val geofencePendingIntent: PendingIntent by lazy {
        val intent = Intent(requireContext(), GeofenceBroadcastReceiver::class.java)
        intent.action = ACTION_GEOFENCE_EVENT
        PendingIntent.getBroadcast(requireContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    }*/

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = GameFragmentBinding.inflate(inflater)
        checkPermissionsAndStartGeofencing()


        viewModel = ViewModelProvider(this, SavedStateViewModelFactory(
            Application(), this)
        ).get(GameViewModel::class.java)

        
        binding.lifecycleOwner = this.viewLifecycleOwner

        //geofencingClient = LocationServices.getGeofencingClient(requireActivity())

        val canvasView = CanvasFrame(requireContext())
        binding.gameLayout.addView(canvasView)

        //val names = resources.getStringArray(R.array.names_for_adapter)
        val randomPokemon = resources.getStringArray(R.array.names_for_adapter)
        val namePicked = "ekans"

        binding.randomSelectBtn.setOnClickListener{

            val rand = randomPokemon.random()
            setQuery(searchQuery + "\"$namePicked\"")


        }
        //TODO() Issue #3
        binding.goToMapBtn.setOnClickListener {
            checkPermissionsAndStartGeofencing()

            val action = GameFragmentDirections.actionGameToMaps()
            view?.findNavController()?.navigate(action)
        }

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        //removeGeofences()
    }
    private fun checkPermissionsAndStartGeofencing() {
        //if (viewModel.geofenceIsActive()) return
        if (foregroundAndBackgroundLocationPermissionApproved()) {
            checkDeviceLocationSettingsAndStartGeofence()
        } else {
            requestForegroundAndBackgroundLocationPermissions()
        }
    }

    private fun checkDeviceLocationSettingsAndStartGeofence(resolve: Boolean = true) {

        val timeInterval: Long = 5000
        val locationRequest = LocationRequest
            .Builder(Priority.PRIORITY_LOW_POWER, timeInterval).build()
        val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
        val settingsClient = LocationServices.getSettingsClient(requireContext())
        val locationSettingsResponseTask = settingsClient.checkLocationSettings(builder.build())

        locationSettingsResponseTask.addOnFailureListener { exception ->
            if (exception is ResolvableApiException && resolve) {
                try {
                    exception.startResolutionForResult(
                        requireActivity(),
                        REQUEST_TURN_DEVICE_LOCATION_ON
                    )
                } catch (sendEx: IntentSender.SendIntentException) {
                    Timber.tag(TAG)
                        .d( "Error getting location settings resolution: $sendEx.message")
                }
            } else {
                Snackbar.make(
                    binding.gameLayout,
                    R.string.location_required_error, Snackbar.LENGTH_INDEFINITE
                ).setAction(android.R.string.ok) {
                    checkDeviceLocationSettingsAndStartGeofence()
                }.show()
            }
        }
        locationSettingsResponseTask.addOnCompleteListener {
            if (it.isSuccessful) {
                //addGeofenceForPokemon()
            }
        }
    }

    @TargetApi(29)
    private fun foregroundAndBackgroundLocationPermissionApproved(): Boolean {
        val foregroundLocationApproved = (
                PackageManager.PERMISSION_GRANTED ==
                        ActivityCompat.checkSelfPermission(
                            requireContext(),
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ))
        val backgroundPermissionApproved =
            if (runningQorLater) {
                PackageManager.PERMISSION_GRANTED ==
                        ActivityCompat.checkSelfPermission(
                            requireContext(), Manifest.permission.ACCESS_BACKGROUND_LOCATION
                        )
            } else {
                true
            }
        return foregroundLocationApproved && backgroundPermissionApproved
    }

    @TargetApi(29)
    private fun requestForegroundAndBackgroundLocationPermissions() {
        if (foregroundAndBackgroundLocationPermissionApproved())
            return
        var permissionsArray = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
        val resultCode = when {
            runningQorLater -> {
                permissionsArray += Manifest.permission.ACCESS_BACKGROUND_LOCATION
                REQUEST_FOREGROUND_AND_BACKGROUND_PERMISSION_RESULT_CODE
            }
            else -> REQUEST_FOREGROUND_ONLY_PERMISSIONS_REQUEST_CODE
        }
        Timber.tag(TAG).d("Request foreground only location permission")
        ActivityCompat.requestPermissions(requireActivity(), permissionsArray, resultCode)
    }

    @SuppressLint("MissingPermission")
    /*private fun addGeofenceForPokemon() {
        if (viewModel.geofenceIsActive()) return //first check if you have any current geofences, if so, don't add an other
        val currentGeofenceIndex = viewModel.nextGeofenceIndex() //find the current geofence index
        if (currentGeofenceIndex >= GeofenceUtils.NUM_LANDMARKS) {
            removeGeofences()
            viewModel.geofenceActivated()
            return
        }
        val currentGeofenceData = GeofenceUtils.SF_LANDMARK_DATA[currentGeofenceIndex]

        val geofence = Geofence.Builder()
            .setRequestId(currentGeofenceData.id)
            .setCircularRegion(
                currentGeofenceData.latLong.latitude,
                currentGeofenceData.latLong.longitude,
                GeofenceUtils.GEOFENCE_RADIUS_IN_METERS
            )
            .setExpirationDuration(GeofenceUtils.GEOFENCE_EXPIRATION_IN_MILLISECONDS)
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
                        Toast.makeText(
                            requireContext(), R.string.geofences_added,
                            Toast.LENGTH_SHORT
                        )
                            .show()
                        Timber.tag(TAG).e("Add Geofence $geofence.requestId")
                        viewModel.geofenceActivated()
                    }
                    addOnFailureListener {
                        Toast.makeText(
                            requireContext(), R.string.geofences_not_added,
                            Toast.LENGTH_SHORT
                        ).show()
                        if ((it.message != null)) {
                            Timber.tag(TAG).e( it.message!!)
                        }
                    }
                }
            }
        }
    }

    private fun removeGeofences() {
        if (!foregroundAndBackgroundLocationPermissionApproved()) {
            return
        }
        geofencingClient.removeGeofences(geofencePendingIntent).run {
            addOnSuccessListener {
                Timber.tag(TAG).d(getString(R.string.geofences_removed))
                Toast.makeText(
                    requireContext(),
                    R.string.geofences_removed,
                    Toast.LENGTH_SHORT
                ).show()
            }
            addOnFailureListener {
                Timber.tag(TAG).d(getString(R.string.geofences_not_removed))
            }
        }
    }
*/
    companion object {
        internal const val ACTION_GEOFENCE_EVENT =
            "HuntMainActivity.treasureHunt.action.ACTION_GEOFENCE_EVENT"
        private const val REQUEST_FOREGROUND_AND_BACKGROUND_PERMISSION_RESULT_CODE = 33
        private const val REQUEST_FOREGROUND_ONLY_PERMISSIONS_REQUEST_CODE = 34
        private const val REQUEST_TURN_DEVICE_LOCATION_ON = 29
        private const val LOCATION_PERMISSION_INDEX = 0
        private const val BACKGROUND_LOCATION_PERMISSION_INDEX = 1
    }
    //have instructions on how to play, (say you may need to spoof location)
    //click a button to search for a random pokemon
    //need to set up notifications


}