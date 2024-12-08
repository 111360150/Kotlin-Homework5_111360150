package com.example.lab14

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions

class MainActivity : AppCompatActivity(), OnMapReadyCallback {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                1
            )
        } else {
            initMap()
        }
    }
    // 請求位置權限
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1) {
            if (grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
                initMap()
            } else {
                finish()// 沒有權限則結束應用程式
            }
        }
    }

    private fun initMap() {
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment
        mapFragment?.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        googleMap.isMyLocationEnabled = true

        // Add markers
        googleMap.addMarker(
            MarkerOptions()
                .position(LatLng(25.033611, 121.565000))
                .title("台北 101")
                .draggable(true)
        )

        googleMap.addMarker(
            MarkerOptions()
                .position(LatLng(25.047924, 121.517081))
                .title("台北車站")
                .draggable(true)
        )

        // Draw a polyline
        val polylineOptions = PolylineOptions()
            .add(LatLng(25.033611, 121.565000))
            .add(LatLng(25.032728, 121.534905))
            .add(LatLng(25.047924, 121.517081))
            .color(Color.BLACK)
            .width(10f)

        googleMap.addPolyline(polylineOptions)

        // Move camera
        googleMap.moveCamera(
            CameraUpdateFactory.newLatLngZoom(LatLng(25.035, 121.545), 13f)
        )
    }
}
