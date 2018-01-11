package com.zackyzhang.petadoptable.ui.splash

import android.Manifest
import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import com.google.android.gms.location.LocationServices
import com.zackyzhang.petadoptable.ui.main.MainActivity
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.startActivity
import java.util.*

class SplashActivity : AppCompatActivity(), AnkoLogger {

    private val LOCATION_PERMISSION_REQUEST_CODE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions()
        } else {
            setupLastLocation()
        }

    }

    @TargetApi(Build.VERSION_CODES.M)
    private fun requestPermissions() {
        requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<out String>,
                                            grantResults: IntArray) {
        when (requestCode) {
            LOCATION_PERMISSION_REQUEST_CODE ->
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    setupLastLocation()
                } else {
                    startActivity<LocationActivity>()
                    finish()
                }
            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    @SuppressLint("MissingPermission")
    private fun setupLastLocation() {
        val client = LocationServices.getFusedLocationProviderClient(this)
        client.lastLocation
                .addOnSuccessListener {
                    location -> setupZipCode(location!!) {
                        startActivity<MainActivity>(MainActivity.ZIP_CODE to it)
                        finish()
                    }
                }
                .addOnFailureListener {
                    startActivity<LocationActivity>()
                    finish()
                }
    }

    private fun setupZipCode(location: Location, function: (String) -> Unit) {
        info("lat: " + location.latitude + " long: " + location.longitude)
        val geoCoder = Geocoder(this, Locale.getDefault())
        val address = geoCoder.getFromLocation(location.latitude, location.longitude, 1)[0]
        function(address.postalCode)
    }

}