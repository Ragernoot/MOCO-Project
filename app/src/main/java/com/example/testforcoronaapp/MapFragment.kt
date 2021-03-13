package com.example.testforcoronaapp

import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import java.util.jar.Manifest
import com.example.testforcoronaapp.R.id.*


class MapFragment : Fragment() {


    // TODO Hier können wir jetzt irgendwas anderes machen.
    // TODO Der Service ist komplett auf eine Funktion ausgelagert und wir komplett auf der Home Seite angezeigt

   /* lateinit var lm : LocationManager
    lateinit var loc: Location

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)




        if (ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCES_FINCE_LOCATION)!= PackageManager.PERMISSION_GRANTED)
                    ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCES_FINCE_LOCATION),111)


        lm= getService(Context.LOCATION_SERVICE) as LocationManager
        loc = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER)!!

        var ll = object:LocationListener{
            override fun onLocationChanged(p0: Location) {
                reverseGeocode(p0)
            }

            override fun onStatusChanged(p0: String?, p1: Int , p2: Bundle?) {

            }

            override fun onProviderEnabled(p0: String) {



            }

            override fun onProviderDisabled(p0: String) {


            }
        }

        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000 , 100.2f,ll)


    }

    private fun reverseGeocode(loc: Location?) {

        var gc = Geocoder( this, Locale.getDefault())
        var addresses =gc.getFromLocation(loc.latitude,loc.longitude,2)
        var address  = addresses.get(0)

        textView2.setText("Deine derzeitiger Standort\n${address.getAddressLine(0)}\n${address.locality}")

    }

*/
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_map, container, false)
    }
}