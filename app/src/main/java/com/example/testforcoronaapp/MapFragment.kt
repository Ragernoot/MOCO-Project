package com.example.testforcoronaapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.testforcoronaapp.model.statesModel.StatesData
import com.example.testforcoronaapp.repository.Repository
import com.example.testforcoronaapp.viewmodelfactory.MainViewModelFactory
import com.example.testforcoronaapp.viewmodels.HomeFragmentViewModel
import com.google.gson.Gson
import org.json.JSONObject
import java.lang.StringBuilder

class MapFragment : Fragment() {


    // TODO Hier können wir jetzt irgendwas anderes machen.
    // TODO Der Service ist komplett auf eine Funktion ausgelagert und wir komplett auf der Home Seite angezeigt


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_map, container, false)
    }
}