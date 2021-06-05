package com.mysoft.uldbsmarket.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.mysoft.uldbsmarket.R
import com.mysoft.uldbsmarket.databinding.MapFragmentBinding


class MapFragment : Fragment(), OnMapReadyCallback {
    private lateinit var binding : MapFragmentBinding;

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = MapFragmentBinding.inflate(layoutInflater);

        (childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment)
            .getMapAsync(this)

        return binding.root;
    }

    override fun onMapReady(map: GoogleMap) {
        val testMarker = LatLng(53.194846, 45.015790);
        map.addMarker(MarkerOptions()
            .position(testMarker)
            .title("Test")
        )
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(testMarker,15.0f))
    }
}