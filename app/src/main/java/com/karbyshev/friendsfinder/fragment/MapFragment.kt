package com.karbyshev.friendsfinder.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.karbyshev.friendsfinder.util.Constants
import com.karbyshev.friendsfinder.util.Constants.Companion.LOCATION_PERMISSION_REQUEST_CODE
import com.karbyshev.friendsfinder.util.Constants.Companion.NAME
import com.karbyshev.friendsfinder.R
import com.karbyshev.friendsfinder.model.User
import com.karbyshev.friendsfinder.viewModel.MyViewModel
import org.jetbrains.anko.support.v4.toast

class MapFragment : Fragment(), OnMapReadyCallback {

    private lateinit var username: String

    private lateinit var rootView: View
    private lateinit var mMap: GoogleMap
    private lateinit var lastLocation: Location
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var prefs: SharedPreferences? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_main, container, false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prefs = activity!!.getSharedPreferences(Constants.PREFS_FILENAME, 0)
        username = prefs!!.getString(NAME, "root")

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context!!)

        val mapFragment: SupportMapFragment = childFragmentManager.findFragmentById(R.id.mapView) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        mMap = googleMap!!

        val success: Boolean = mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(context, R.raw.style_json))
        if (!success) {
            toast("Something wrong!")
        }

        mMap.uiSettings.isZoomControlsEnabled = true

        setUpMap()

        addMarker()
    }

    private fun setUpMap() {
        var myLat: Double
        var myLng: Double

        if (ActivityCompat.checkSelfPermission(context!!,
                        android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity!!,
                    arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
            return
        }

        mMap.isMyLocationEnabled = true

        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                myLat = location.latitude
                myLng = location.longitude
                lastLocation = location
                val currentLatLng = LatLng(myLat, myLng)
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 12f))
                sendMyLocation(myLat, myLng)
            }
        }
    }

    private fun sendMyLocation(lat: Double, lng: Double) {

        val ref: DatabaseReference = FirebaseDatabase.getInstance().getReference("users")

        val user = User(username, lat.toString(), lng.toString())

        ref.child(username).setValue(user).addOnCompleteListener {
        }
    }

    private fun addMarker() {
        var model: MyViewModel = ViewModelProviders.of(this).get(MyViewModel::class.java)
        model.getUsers().observe(this, Observer { newUsers ->
            mMap.clear()
            for (i in newUsers!!.withIndex()){
                var lat: Double = newUsers.get(i.index).lat.toDouble()
                var lng: Double = newUsers.get(i.index).lng.toDouble()
                var name: String = newUsers.get(i.index).name

                createMarker(lat, lng, name)
            }
        })
    }

    private fun createMarker(lat: Double, lng: Double, name: String) : Marker{

        return mMap.addMarker(MarkerOptions()
                .position(LatLng(lat, lng))
                .anchor(0.5f, 0.5f)
                .title(name))
    }
}

