package com.example.pokesearch.ui.game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.pokesearch.R
import com.example.pokesearch.databinding.MapsFragmentBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsFragment: Fragment(), OnMapReadyCallback {

    private lateinit var binding: MapsFragmentBinding
    private lateinit var map: GoogleMap

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = MapsFragmentBinding.inflate(inflater)

        val mapFragment = childFragmentManager.findFragmentById(R.id.mapView) as SupportMapFragment
        mapFragment.getMapAsync(this)

        return(binding.root)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        map.isMyLocationEnabled
        val sf = LatLng(37.7921469, -122.4175258)
        val zoomLevel = 15f
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(sf, zoomLevel))
        setPoiClick(map)
    }

    private fun setPoiClick(map: GoogleMap) {
        map.clear()
        map.setOnPoiClickListener { poi ->
            val poiMarker = map.addMarker(
                MarkerOptions()
                    .position(poi.latLng)
                    .title(poi.name))
            poiMarker?.showInfoWindow()
        }
    }
}
