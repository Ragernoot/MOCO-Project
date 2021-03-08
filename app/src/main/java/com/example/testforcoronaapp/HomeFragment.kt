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
import com.example.testforcoronaapp.repository.Repository
import com.example.testforcoronaapp.utils.Constants
import com.example.testforcoronaapp.viewmodelfactory.MainViewModelFactory
import com.example.testforcoronaapp.viewmodels.MainViewModel
import okhttp3.Cache.Companion.key
import java.util.*

class HomeFragment : Fragment() {

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onStart() {
        super.onStart()
        val view = view

        if (view != null) {
            val textView = view.findViewById<TextView>(R.id.nav_home_textView)
//        val lastUpdateView = findViewById<TextView>(R.id.jsonDataLastUpdate)

            val repository = Repository()
            val viewModelFactory = MainViewModelFactory(repository)
            viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
            Log.e("tag", "URL URL URL " + Constants.BASE_URL)
            //viewModel.getStatesDataViewModel()

            viewModel.getDistrictDataViewModel()
            viewModel.districtDataLiveData.observe(this, Observer { response ->

                if(response.isSuccessful) {


//            lastUpdateView.text = response.lastUpdate
//                val test = response.data?.actualDataAgs
//
//
//                Log.e("tag", "WAS KOMMT HIER WOHL RAUS " + test)


                    var content = ""
                    for (e in response.body()!!.districts!!) {
                        content += "Name: " + e.name + "\n"
                        content += "Code: " + e.county + "\n"
                        content += "Count: " + e.count + "\n"
                        content += "Fälle pro Woche: " + e.weekIncidence + "\n"
                        content += "Fälle pro 100k: " + e.casesPer100k + "\n"
                        content += "Tode: " + e.deaths + "\n\n"

                        textView.text = content
                    }

                    textView.append(response.body()!!.districts!!.size.toString())
                } else {
                    Log.e("WrongHOME", "Something went Wrong")
                }
            })
        }
    }
}