package mezzari.torres.lucas.realtimelocationapp.flow.map

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import mezzari.torres.lucas.conductor.source.generic.implementation.BaseActivity
import mezzari.torres.lucas.realtimelocationapp.R
import mezzari.torres.lucas.realtimelocationapp.flow.MainConductor

class MapsActivity : BaseActivity(), OnMapReadyCallback, LocationListener {
    override val conductor = MainConductor

    val viewModel: MapsActivityViewModel by lazy {
        MapsActivityViewModel()
    }

    private var mLocationManager: LocationManager? = null
    private lateinit var mMap: GoogleMap
    private var userMarker: Marker? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        supportActionBar?.hide()

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        mLocationManager = getSystemService(LOCATION_SERVICE) as? LocationManager
        getLocationPermission()
    }

    override fun onRestart() {
        super.onRestart()
        viewModel.connectSocket()
    }

    override fun onStop() {
        super.onStop()
        viewModel.disconnectSocket()
    }

    private fun getLocationPermission() {
        if (ContextCompat.checkSelfPermission(
                this.applicationContext,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            updateMapPosition()
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                0
            )
        }
    }

    @SuppressLint("MissingPermission")
    private fun updateMapPosition() {
        mLocationManager?.
            requestLocationUpdates(LocationManager.GPS_PROVIDER, 1, 100F, this)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            updateMapPosition()
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
    }

    override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {
        //DEPRECATED
    }

    @SuppressLint("MissingPermission")
    override fun onProviderEnabled(p0: String?) {
        mLocationManager?.
            requestLocationUpdates(LocationManager.GPS_PROVIDER, 1, 100F, this)
    }

    override fun onProviderDisabled(p0: String?) {
        onLocationChanged(null)
    }

    override fun onLocationChanged(p0: Location?) {
        userMarker?.run {
            remove()
        }

        p0?.run {
            val position = LatLng(latitude, longitude)
            val zoomLevel = mMap.minZoomLevel + mMap.maxZoomLevel / 2
            userMarker = mMap.addMarker(
                MarkerOptions()
                    .position(position)
                    .title("Your position - ${viewModel.username}")
                    .icon(
                        BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)
                    )
            )
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(position, zoomLevel))
        }
    }
}