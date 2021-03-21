package com.example.testforcoronaapp

import android.content.ContentValues.TAG
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageSwitcher
import android.widget.ImageView
import android.widget.ViewSwitcher
import androidx.fragment.app.Fragment
import com.example.testforcoronaapp.utils.Constants.Companion.BASE_URL
import com.squareup.picasso.Picasso
import okhttp3.*
import java.io.ByteArrayInputStream
import java.io.IOException


class MapFragment : Fragment(), View.OnClickListener {

    private lateinit var fragmentContext : Context
    private lateinit var imageView : ImageView
    private lateinit var buttonSwitch : Button
    private var districtImage = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView =  inflater.inflate(R.layout.fragment_map, container, false)

        fragmentContext = rootView.context
        buttonSwitch = rootView.findViewById(R.id.button_image_switch)
        imageView = rootView.findViewById(R.id.district_image_view)

        return rootView
    }

    override fun onStart() {
        super.onStart()

        buttonSwitch.setOnClickListener(this)
        Picasso.get().load(BASE_URL + "map/districts").into(imageView)
    }

    override fun onClick(view : View){
        if(districtImage) {
            Picasso.get().load(BASE_URL + "map/states").into(imageView)
            districtImage = false
        } else if(!districtImage) {
            Picasso.get().load(BASE_URL + "map/districts").into(imageView)
            districtImage = true
        }
    }
}