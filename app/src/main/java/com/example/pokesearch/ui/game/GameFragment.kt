package com.example.pokesearch.ui.game

import android.Manifest
import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.Application
import android.app.PendingIntent
import android.content.Intent
import android.content.Intent.getIntent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.SavedStateViewModelFactory
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.pokesearch.GeofenceBroadcastReceiver
import com.example.pokesearch.R
import com.example.pokesearch.databinding.GameFragmentBinding
import com.example.pokesearch.model.Pokemon
import com.example.pokesearch.ui.CanvasFrame
import com.example.pokesearch.utils.GeofencingConstants
import com.example.pokesearch.utils.bindPokemonSprite
import com.example.pokesearch.utils.createChannel
import com.example.pokesearch.utils.setQuery
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingClient
import com.google.android.gms.location.GeofencingRequest
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.Priority
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import timber.log.Timber
import java.util.Random

class GameFragment : Fragment() {

    private lateinit var binding: GameFragmentBinding
    //private lateinit var viewModel: GameViewModel
    private lateinit var geofencingClient: GeofencingClient
    private val gameViewModel by viewModels<GameViewModel>()
    private lateinit var geoViewModel: GameViewModel
    private val runningQorLater = Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q
    private var searchQuery = " name = "

    private val geofencePendingIntent: PendingIntent by lazy {
        val intent = Intent(requireContext(), GeofenceBroadcastReceiver::class.java)
        intent.action = ACTION_GEOFENCE_EVENT
        PendingIntent.getBroadcast(requireContext(), 0, intent, PendingIntent.FLAG_IMMUTABLE)
    }


    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = GameFragmentBinding.inflate(inflater)

        /*
        viewModel = ViewModelProvider(
            requireActivity(),
            SavedStateViewModelFactory(requireActivity().application,
                requireActivity()))[GameViewModel::class.java]
                */

        binding.gameViewModel = gameViewModel
        geoViewModel = ViewModelProvider(
            requireActivity(),
            SavedStateViewModelFactory(requireActivity().application,
                requireActivity()))[GameViewModel::class.java]

        binding.lifecycleOwner = this.viewLifecycleOwner

        geofencingClient = LocationServices.getGeofencingClient(requireContext())

        createChannel(requireContext())

        val canvasView = CanvasFrame(requireContext())
        binding.gameLayout.addView(canvasView)

        val randomPokemon = resources.getStringArray(R.array.names_for_adapter)
        val random = Random(System.currentTimeMillis())
        binding.randomSelectBtn.setOnClickListener{
            val index = random.nextInt(randomPokemon.size-1)
            Timber.i(randomPokemon[index])
            setQuery("$searchQuery\"${randomPokemon[index].lowercase()}\"")

            val id = findNavController().currentDestination?.id
            findNavController().popBackStack(id!!, true)
            findNavController().navigate(id)
        }

        //TODO() Issue #3
        binding.goToMapBtn.setOnClickListener {
            checkPermissionsAndStartGeofencing()
            //val action = GameFragmentDirections.actionGameToMaps()
            //view?.findNavController()?.navigate(action)
        }

        return binding.root
    }



    @RequiresApi(Build.VERSION_CODES.Q)
    private fun checkPermissionsAndStartGeofencing() {
        if (geoViewModel.geofenceIsActive()) return
        if (foregroundAndBackgroundLocationPermissionApproved()) {
            checkDeviceLocationSettingsAndStartGeofence()
        } else {
            requestForegroundAndBackgroundLocationPermissions()
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun requestForegroundAndBackgroundLocationPermissions() {
        if (foregroundAndBackgroundLocationPermissionApproved())
            return
        var permissionsArray = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
        val resultCode = when {
            runningQorLater -> {
                permissionsArray += Manifest.permission.ACCESS_BACKGROUND_LOCATION
                Timber.i("Request foreground and background location permission")
                REQUEST_FOREGROUND_AND_BACKGROUND_PERMISSION_RESULT_CODE
            }
            else -> REQUEST_FOREGROUND_ONLY_PERMISSIONS_REQUEST_CODE
        }
        Timber.i("Request foreground only location permission")
        ActivityCompat.requestPermissions(
            requireActivity(),
            permissionsArray,
            resultCode
        )
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun checkDeviceLocationSettingsAndStartGeofence(resolve: Boolean = true) {
        val timeInterval: Long = 5000
        val locationRequest = LocationRequest
            .Builder(Priority.PRIORITY_LOW_POWER, timeInterval).build()
        val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
        val settingsClient = LocationServices.getSettingsClient(requireContext())
        val locationSettingsResponseTask = settingsClient.checkLocationSettings(builder.build())

        locationSettingsResponseTask.addOnFailureListener { exception ->
            if (exception is ResolvableApiException && resolve){
                try {
                    exception.startResolutionForResult(requireActivity(),
                        REQUEST_TURN_DEVICE_LOCATION_ON)
                } catch (sendEx: IntentSender.SendIntentException) {
                    Timber.i("Error getting location settings resolution: $sendEx.message")
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
            if ( it.isSuccessful ) {
                addGeofenceForClue()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun foregroundAndBackgroundLocationPermissionApproved(): Boolean {
        val foregroundLocationApproved = (
                PackageManager.PERMISSION_GRANTED ==
                        ActivityCompat.checkSelfPermission(requireContext(),
                            Manifest.permission.ACCESS_FINE_LOCATION))
        val backgroundPermissionApproved =
            if (runningQorLater) {
                PackageManager.PERMISSION_GRANTED ==
                        ActivityCompat.checkSelfPermission(
                            requireContext(), Manifest.permission.ACCESS_BACKGROUND_LOCATION
                        )
            } else {
                true
            }
        Timber.i("fore is $foregroundLocationApproved and back is $backgroundPermissionApproved")
        return foregroundLocationApproved && backgroundPermissionApproved
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_TURN_DEVICE_LOCATION_ON) {
            checkDeviceLocationSettingsAndStartGeofence(false)
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    @SuppressLint("MissingPermission")
    private fun addGeofenceForClue() {
        if (geoViewModel.geofenceIsActive()) return //first check if you have any current geofences, if so, don't add an other
        val currentGeofenceIndex = geoViewModel.nextGeofenceIndex() //find the current geofence index
        if(currentGeofenceIndex >= GeofencingConstants.NUM_LANDMARKS) {
            removeGeofences()
            geoViewModel.geofenceActivated()
            return
        }
        val currentGeofenceData = GeofencingConstants.SF_LANDMARK_DATA[currentGeofenceIndex]

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
                        geoViewModel.geofenceActivated()
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

    /**
     * Removes geofences. This method should be called after the user has granted the location
     * permission.
     */
    @RequiresApi(Build.VERSION_CODES.Q)
    private fun removeGeofences() {
        if (!foregroundAndBackgroundLocationPermissionApproved()) {
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

    /*@RequiresApi(Build.VERSION_CODES.Q)
    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        val extras = intent?.extras
        if(extras != null){
            if(extras.containsKey(GeofencingConstants.EXTRA_GEOFENCE_INDEX)){
                geoViewModel.updateHint(extras.getInt(GeofencingConstants.EXTRA_GEOFENCE_INDEX))
                checkPermissionsAndStartGeofencing()
            }
        }
    }*/

    companion object {
        internal const val ACTION_GEOFENCE_EVENT =
            "ACTION_GEOFENCE_EVENT"
        private const val REQUEST_FOREGROUND_AND_BACKGROUND_PERMISSION_RESULT_CODE = 33
        private const val REQUEST_FOREGROUND_ONLY_PERMISSIONS_REQUEST_CODE = 34
        private const val REQUEST_TURN_DEVICE_LOCATION_ON = 29
        private const val LOCATION_PERMISSION_INDEX = 0
        private const val BACKGROUND_LOCATION_PERMISSION_INDEX = 1
    }

}