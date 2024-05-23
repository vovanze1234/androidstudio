package com.example.yandexdriver

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.directions.DirectionsFactory
import com.yandex.mapkit.directions.driving.DrivingRouter
import com.yandex.mapkit.directions.driving.DrivingSession
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.MapObjectCollection
import com.yandex.mapkit.mapview.MapView
import android.Manifest
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.yandex.mapkit.RequestPoint
import com.yandex.mapkit.RequestPointType
import com.yandex.mapkit.directions.driving.DrivingOptions
import com.yandex.mapkit.directions.driving.DrivingRoute
import com.yandex.mapkit.directions.driving.VehicleOptions
import com.yandex.mapkit.map.IconStyle
import com.yandex.runtime.Error
import com.yandex.runtime.image.ImageProvider
import com.yandex.runtime.network.NetworkError
import com.yandex.runtime.network.RemoteError

class MainActivity : AppCompatActivity(), DrivingSession.DrivingRouteListener {

    private var task = true
    private var isWork = false
    private val REQUEST_CODE_PERMISSION = 100
    private var ROUTE_START_LOCATION = Point(55.670005, 37.479894)
    private var ROUTE_END_LOCATION = Point(55.794229, 37.700772)
    private val SCREEN_CENTER = Point(
        (ROUTE_START_LOCATION.latitude + ROUTE_END_LOCATION.latitude) / 2,
        (ROUTE_START_LOCATION.longitude + ROUTE_END_LOCATION.longitude) / 2
    )
    private lateinit var mapView: MapView
    private lateinit var mapObjects: MapObjectCollection
    private lateinit var drivingRouter: DrivingRouter
    private lateinit var drivingSession: DrivingSession
    private val colors = intArrayOf(0xFFFF0000.toInt(), 0xFF00FF00.toInt(), 0x00FFBBBB.toInt(), 0xFF0000FF.toInt())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        MapKitFactory.initialize(this)
        DirectionsFactory.initialize(this)

        mapView = findViewById(R.id.mapview)
        mapView.map.isRotateGesturesEnabled = false
        mapView.map.move(CameraPosition(SCREEN_CENTER, 10.0f, 0.0f, 0.0f))
        drivingRouter = DirectionsFactory.getInstance().createDrivingRouter()
        mapObjects = mapView.map.mapObjects.addCollection()

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            isWork = true
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ), REQUEST_CODE_PERMISSION)
        }

        if (isWork) {
            submitRequest()
        }
    }

    private fun submitRequest() {
        val drivingOptions = DrivingOptions()
        val vehicleOptions = VehicleOptions()
        drivingOptions.routesCount = 4

        if (task) {
            ROUTE_START_LOCATION = Point( 55.850474, 38.436396 )
            ROUTE_END_LOCATION = Point(55.79424375563888, 37.699661753948455)
        }
        val requestPoints = ArrayList<RequestPoint>()
        requestPoints.add(RequestPoint(ROUTE_START_LOCATION, RequestPointType.WAYPOINT, null))
        requestPoints.add(RequestPoint(ROUTE_END_LOCATION, RequestPointType.WAYPOINT, null))
        drivingSession = drivingRouter.requestRoutes(requestPoints, drivingOptions, vehicleOptions, this)
    }

    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
        mapView.onStart()
    }

    override fun onStop() {
        super.onStop()
        MapKitFactory.getInstance().onStop()
        mapView.onStop()
    }

    override fun onDrivingRoutes(list: List<DrivingRoute>) {
        for (i in list.indices) {
            val color = colors[i]
            val polyline = mapObjects.addPolyline(list[i].geometry)
            polyline.setStrokeColor(color)
        }

        if (task) {
            val marker = mapView.map.mapObjects.addPlacemark(ROUTE_END_LOCATION,
                ImageProvider.fromResource(this, R.drawable.search_layer_pin_selected_default))
            marker.setIconStyle(IconStyle().setScale(1f))
            marker.addTapListener { _, _ ->
                Toast.makeText(applicationContext, "МИРЭА Стромынка 20", Toast.LENGTH_SHORT).show()
                false
            }
        }
    }

    override fun onDrivingRoutesError(error: Error) {
        val errorMesage = getString(R.string.unknown_error_message)
        val errorMessage = when (error) {
            is RemoteError -> getString(R.string.remote_error_message)
            is NetworkError -> getString(R.string.network_error_message)
            else -> errorMesage
        }
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSION) {
            isWork = grantResults.isNotEmpty() &&
                    (grantResults[0] == PackageManager.PERMISSION_GRANTED
                            || grantResults[1] == PackageManager.PERMISSION_GRANTED)
        }

        if (isWork) {
            submitRequest()
        }
    }
}