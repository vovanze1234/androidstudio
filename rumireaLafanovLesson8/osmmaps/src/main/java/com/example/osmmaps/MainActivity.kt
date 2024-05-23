package com.example.osmmaps

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.preference.PreferenceManager
import com.example.osmmaps.databinding.ActivityMainBinding
import org.osmdroid.api.IMapController
import org.osmdroid.config.Configuration
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.ScaleBarOverlay
import org.osmdroid.views.overlay.compass.CompassOverlay
import org.osmdroid.views.overlay.compass.InternalCompassOrientationProvider
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay

class MainActivity : AppCompatActivity() {
    private val REQUEST_CODE_PERMISSION = 100
    private lateinit var mapView: MapView
    private lateinit var locationNewOverlay: MyLocationNewOverlay
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Configuration.getInstance().load(applicationContext, PreferenceManager.getDefaultSharedPreferences(applicationContext))
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            funcAll()
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ), REQUEST_CODE_PERMISSION)
        }
    }

    override fun onResume() {
        super.onResume()
        Configuration.getInstance().load(applicationContext, PreferenceManager.getDefaultSharedPreferences(applicationContext))
        if (::mapView.isInitialized) {
            mapView.onResume()
        }
    }

    override fun onPause() {
        super.onPause()
        Configuration.getInstance().save(applicationContext, PreferenceManager.getDefaultSharedPreferences(applicationContext))
        if (::mapView.isInitialized) {
            mapView.onPause()
        }
    }

    private fun setMarker(nameMarker: String, point: GeoPoint) {
        val marker = Marker(mapView)
        marker.position = point
        marker.setOnMarkerClickListener { marker, mapView ->
            Toast.makeText(applicationContext, "Описание: $nameMarker", Toast.LENGTH_SHORT).show()
            true
        }
        marker.icon = ContextCompat.getDrawable(
            applicationContext,
            org.osmdroid.library.R.drawable.osm_ic_follow_me_on
        )
        marker.title = nameMarker
        mapView.overlays.add(marker)
        mapView.invalidate()  // Обновляем карту после добавления метки
    }

    private fun funcAll() {
        mapView = binding.mapView
        mapView.setZoomRounding(true)
        mapView.setMultiTouchControls(true)
        val mapController: IMapController = mapView.controller
        mapController.setZoom(15.0)
        val startPoint = GeoPoint(55.753340, 37.621245)
        mapController.setCenter(startPoint)

        locationNewOverlay = MyLocationNewOverlay(
            GpsMyLocationProvider(applicationContext),
            mapView
        )
        locationNewOverlay.enableMyLocation()
        mapView.overlays.add(locationNewOverlay)

        val compassOverlay = CompassOverlay(
            applicationContext,
            InternalCompassOrientationProvider(applicationContext),
            mapView
        )
        compassOverlay.enableCompass()
        mapView.overlays.add(compassOverlay)

        val context: Context = applicationContext
        val dm = context.resources.displayMetrics
        val scaleBarOverlay = ScaleBarOverlay(mapView)
        scaleBarOverlay.setCentred(true)
        scaleBarOverlay.setScaleBarOffset(dm.widthPixels / 2, 10)
        mapView.overlays.add(scaleBarOverlay)

        setMarker("МИРЭА Стромынка", GeoPoint(55.794229, 37.700772))
        setMarker("Ногинск", GeoPoint(55.850474, 38.436396))
        setMarker("Вэб арена", GeoPoint(55.792194, 37.515964))
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                funcAll()
            }
        }
    }
}
