package com.bashirli.buyme.view.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context.LOCATION_SERVICE
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.bashirli.buyme.R
import com.bashirli.buyme.databinding.FragmentMapsBinding
import com.bashirli.buyme.view.bottomsheet.LocationBottomSheetFragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.snackbar.Snackbar

class MapsFragment : Fragment(),GoogleMap.OnMapLongClickListener {
    private lateinit var gMap:GoogleMap
    private lateinit var locationManager: LocationManager
    private lateinit var locationListener: LocationListener
    private lateinit var permissionLauncher:ActivityResultLauncher<String>
    private lateinit var binding:FragmentMapsBinding
    private var info:Boolean?=null
    private lateinit var sharedPreferences: SharedPreferences
    private var selectedLat:Double=0.0
    private var selectedLong:Double=0.0


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding= FragmentMapsBinding.bind(view)
        sharedPreferences=requireActivity().getSharedPreferences(
            "com.bashirli.buyme.view.fragment",
            MODE_PRIVATE)
        info=false
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        activityLaunch()
        mapFragment?.getMapAsync(callback)

        binding.continueButton.setOnClickListener {
            var myFragment=LocationBottomSheetFragment(LatLng(selectedLat,selectedLong))
            myFragment.show(requireActivity().supportFragmentManager,"bottomFragment")
        }

       }


    @SuppressLint("MissingPermission")
    private val callback = OnMapReadyCallback { googleMap ->
        gMap=googleMap
        gMap.setOnMapLongClickListener(this)
    locationManager=requireActivity().getSystemService(LOCATION_SERVICE) as LocationManager
        locationListener=object:LocationListener{
            override fun onLocationChanged(location: Location) {
                info=sharedPreferences.getBoolean("info",false)
                if(!info!!){
                    var latLng=LatLng(location.latitude,location.longitude)
                    gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,15f))
                    sharedPreferences.edit().putBoolean("info",true).apply()
                }
            }
        }


        if(ContextCompat.checkSelfPermission(requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
            !=PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(),Manifest.permission.ACCESS_FINE_LOCATION)){
                   Snackbar.make(binding.root,R.string.permission,Snackbar.LENGTH_INDEFINITE)
                       .setAction(R.string.givePer){
                    //permission
                           permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                   }.show()

            }else{
                //permission
                permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }
            else{
            //access granted
            locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                0,
                4f,
                locationListener)
            gMap.isMyLocationEnabled=true
            val lastLocation=locationManager.getLastKnownLocation(
                LocationManager.GPS_PROVIDER
            )
            if(lastLocation!=null){
                val lastKnown=LatLng(lastLocation.latitude,lastLocation.longitude)
                gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                    lastKnown,17f
                ))
            }
        }

    }

    private fun activityLaunch(){
        permissionLauncher=registerForActivityResult(
            ActivityResultContracts.RequestPermission()){
            if(it){
                //permission granted
                if(ContextCompat.checkSelfPermission(requireContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED){
                    locationManager.requestLocationUpdates(
                        LocationManager.GPS_PROVIDER,
                        0,
                        4f,
                        locationListener
                    )
                    gMap.isMyLocationEnabled=true
                    val lastLocation=locationManager.getLastKnownLocation(
                        LocationManager.GPS_PROVIDER
                    )
                    if(lastLocation!=null){
                    val lastKnown=LatLng(lastLocation.latitude,lastLocation.longitude)

                        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                            lastKnown,17f
                        ))
                    }
                }

            }else{
               var alert= AlertDialog.Builder(requireContext())
                alert.setTitle(R.string.attention).setMessage(R.string.locationAsk)
                    .setNegativeButton(R.string.no){dialog,which->
                        return@setNegativeButton
                    }.setPositiveButton(R.string.yes){dialog,which->
                        val intent=Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                        val uri = Uri.fromParts("package",
                            requireContext().packageName, null)
                        intent.setData(uri)
                        startActivity(intent)
                    }.create().show()
            }
        }
    }



    override fun onMapLongClick(p0: LatLng) {
       if(binding.continueButton.visibility==View.GONE){
           binding.continueButton.visibility=View.VISIBLE
       }
        gMap.clear()
        selectedLat=p0.latitude
        selectedLong=p0.longitude
        gMap.addMarker(MarkerOptions().position(p0).title("Selected location"))
        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(p0,20f))
    }


}