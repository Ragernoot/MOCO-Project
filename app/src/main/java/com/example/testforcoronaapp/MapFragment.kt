package com.example.testforcoronaapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.testforcoronaapp.repository.Repository
import com.example.testforcoronaapp.viewmodelfactory.MainViewModelFactory
import com.example.testforcoronaapp.viewmodels.MainViewModel

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

//            lastUpdateView.append(response.lastUpdate.toString())
//
//            textView.append(response.states!!.size.toString())

                var content = " "

                for (e in response.states!!) {
                    content += "Name: " + e.name + "\n"
                    content += "Code: " + e.code + "\n"
                    content += "Count: " + e.count + "\n"
                    content += "Fälle pro Woche: " + e.weekIncidence + "\n"
                    content += "Fälle pro 100k: " + e.casesPer100k + "\n"
                    content += "Tode: " + e.deaths + "\n\n"

                    textView.text = content
                }

                textView.append(response.states!!.size.toString())

            })
        }
    }
}