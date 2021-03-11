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
import com.example.testforcoronaapp.model.districtModel.ActualData
import com.example.testforcoronaapp.repository.Repository
import com.example.testforcoronaapp.viewmodelfactory.MainViewModelFactory
import com.example.testforcoronaapp.viewmodels.MainViewModel
import com.google.gson.Gson
import org.json.JSONObject
import java.lang.StringBuilder

class MapFragment : Fragment() {

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onStart() {
        super.onStart()
        val view = view

        if (view != null) {
            val textView = view.findViewById<TextView>(R.id.nav_map_textView)
//        val lastUpdateView = findViewById<TextView>(R.id.jsonDataLastUpdate)

            val repository = Repository()
            val viewModelFactory = MainViewModelFactory(repository)
            viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
            viewModel.getStatesDataViewModel()

            //viewModel.getDistrictDataViewModel()
            viewModel.statesDataLiveData.observe(this, Observer { response ->
                if(response.isSuccessful){
                    // TODO Hier muss noch genau das selbe wie bei den Districts gemacht werden,
                    // TODO Werde ich die Tage aber machen!
                } else {
                    Log.e("WrongMAP", "Something went Wrong")
                }
            })
        }
    }
}