package com.midwestpilotcars.views.activities

import `in`.madapps.placesautocomplete.PlaceAPI
import `in`.madapps.placesautocomplete.adapter.PlacesAutoCompleteAdapter
import `in`.madapps.placesautocomplete.listener.OnPlacesDetailsListener
import `in`.madapps.placesautocomplete.model.Address
import `in`.madapps.placesautocomplete.model.Place
import `in`.madapps.placesautocomplete.model.PlaceDetails
import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.widget.AdapterView
import android.widget.Toast
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationListener
import com.google.android.gms.maps.OnMapReadyCallback
import com.midwestpilotcars.R
import kotlinx.android.synthetic.main.activity_map.autoCompleteEditText
import kotlinx.android.synthetic.main.activity_map.btn_submit
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.GoogleMap
import android.location.Location
import com.google.android.gms.maps.SupportMapFragment
import android.content.pm.PackageManager
import android.databinding.DataBindingUtil
import android.location.Geocoder
import android.support.v4.content.ContextCompat
import android.os.Build
import android.support.v4.app.FragmentActivity
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.midwestpilotcars.base.BaseActivity
import com.midwestpilotcars.constants.AppConstants
import java.util.*


/**
 * The main activity show cases an example of how to use the places auto complete api
 */
class MapActivity : BaseActivity(), OnMapReadyCallback,
        LocationListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {
    override fun onClick(v: View?) {

        when (v) {
            btn_submit -> {
                var intent = Intent()
                intent.putExtra("Address", autoCompleteEditText.text.toString())
                intent.putExtra("Lat", lat)
                intent.putExtra("Long", long)
                setResult(AppConstants.GET_LOCATION_RESPONSE_CODE, intent)
                finish()
            }
        }
    }

    private var mMap: GoogleMap? = null
    var mLastLocation: Location? = null
    lateinit var mCurrLocationMarker: Marker
    lateinit var mGoogleApiClient: GoogleApiClient
    lateinit var mLocationRequest: LocationRequest

    val placesApi = PlaceAPI.Builder()
            .apiKey("AIzaSyAj2mPqaBLScPUND6GGcVvaWGOPOAYKETg")
            .build(this@MapActivity)

    var street = ""
    var city = ""
    var state = ""
    var country = ""
    var zipCode = ""
    var lat = ""
    var long = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        supportActionBar!!.title = getString(R.string.title_map)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        setMap()

        autoCompleteEditText.setAdapter(PlacesAutoCompleteAdapter(this, placesApi))
        autoCompleteEditText.onItemClickListener =
                AdapterView.OnItemClickListener { parent, _, position, _ ->
                    val place = parent.getItemAtPosition(position) as Place
                    autoCompleteEditText.append(place.description)
                    getPlaceDetails(place.id)
                }
        btn_submit.setOnClickListener(this)
    }

    private fun setMap() {
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)
    }

    private fun getPlaceDetails(placeId: String) {
        placesApi.fetchPlaceDetails(placeId, object :
                OnPlacesDetailsListener {
            override fun onError(errorMessage: String) {
                Toast.makeText(this@MapActivity, errorMessage, Toast.LENGTH_SHORT).show()
            }
            override fun onPlaceDetailsFetched(placeDetails: PlaceDetails) {
                setupUI(placeDetails)
            }
        })
    }

    private fun setupUI(placeDetails: PlaceDetails) {
        val address = placeDetails.address
        parseAddress(address)
        setMapMarker(placeDetails)
    }

    private fun setMapMarker(placeDetails: PlaceDetails) {
        runOnUiThread {
            if (::mCurrLocationMarker.isInitialized)
                mCurrLocationMarker.remove()
            var latLng = LatLng(placeDetails.lat, placeDetails.lng);
            var markerOptions = MarkerOptions()
            markerOptions.position(latLng)
            markerOptions.title("New Position")
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
            mCurrLocationMarker = mMap!!.addMarker(markerOptions)
            //move map camera
            mMap!!.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            mMap!!.animateCamera(CameraUpdateFactory.zoomTo(15F))
            lat = placeDetails.lat.toString()
            long = placeDetails.lng.toString()
            //stop location updates
            mCurrLocationMarker.isDraggable = true
            mMap!!.setOnMarkerDragListener(object : GoogleMap.OnMarkerDragListener {
                override fun onMarkerDragEnd(p0: Marker?) {
                    mCurrLocationMarker.snippet = mCurrLocationMarker.position.latitude.toString();
                    mMap!!.animateCamera(CameraUpdateFactory.newLatLng(mCurrLocationMarker.position));
                    setAddress(mCurrLocationMarker.position.latitude, mCurrLocationMarker.position.longitude)
                }

                override fun onMarkerDragStart(p0: Marker?) {

                }

                override fun onMarkerDrag(p0: Marker?) {
                }
            });
        }
    }

    private fun setAddress(latitude: Double, longitude: Double) {
        var geocoder = Geocoder(this, Locale.getDefault())
        var addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
        var address = addresses[0].getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
        autoCompleteEditText.append(address)
    }

    private fun parseAddress(address: ArrayList<Address>) {
        (0 until address.size).forEach { i ->
            when {
                address[i].type.contains("street_number") -> street += address[i].shortName + " "
                address[i].type.contains("route") -> street += address[i].shortName
                address[i].type.contains("locality") -> city += address[i].shortName
                address[i].type.contains("administrative_area_level_1") -> state += address[i].shortName
                address[i].type.contains("country") -> country += address[i].shortName
                address[i].type.contains("postal_code") -> zipCode += address[i].shortName
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient()
                mMap!!.isMyLocationEnabled = true
            }
        } else {
            buildGoogleApiClient()
            mMap!!.isMyLocationEnabled = true
        }

    }

    protected fun buildGoogleApiClient() {
        mGoogleApiClient = GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build()
        mGoogleApiClient.connect()
    }

    override fun onConnected(bundle: Bundle?) {
        mLocationRequest = LocationRequest()
        mLocationRequest.interval = 1000;
        mLocationRequest.fastestInterval = 1000;
        mLocationRequest.priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY;
        if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }

    override fun onConnectionSuspended(i: Int) {

    }

    override fun onLocationChanged(location: Location) {
        mLastLocation = location
        if (::mCurrLocationMarker.isInitialized)
            mCurrLocationMarker.remove()
        //Place current location marker
        var latLng = LatLng(location.latitude, location.longitude)
        var markerOptions = MarkerOptions()
        markerOptions.position(latLng);
        markerOptions.title("Current Position");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
        mCurrLocationMarker = mMap!!.addMarker(markerOptions);

        //move map camera
        mMap!!.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap!!.animateCamera(CameraUpdateFactory.zoomTo(15F))
        setAddress(location.latitude,location.longitude)

        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }
    }

    override fun onConnectionFailed(p0: ConnectionResult) {

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onResume() {
        super.onResume()

    }

}