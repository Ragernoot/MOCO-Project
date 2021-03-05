package com.example.testforcoronaapp.views

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.testforcoronaapp.R
import com.example.testforcoronaapp.repository.Repository
import com.example.testforcoronaapp.viewmodelfactory.MainViewModelFactory
import com.example.testforcoronaapp.viewmodels.MainViewModel

class IndexActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val testView = findViewById<TextView>(R.id.testView)
//        val textView = findViewById<TextView>(R.id.jsonData)
//        val lastUpdateView = findViewById<TextView>(R.id.jsonDataLastUpdate)

        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        //viewModel.getStatesDataViewModel()

        viewModel.getDistrictDataViewModel()
        viewModel.districtDataLiveData.observe(this, Observer { response ->

//            lastUpdateView.text = response.lastUpdate
//
//            testView.text = response.districts!!.size.toString()

            var content = " "
            for(e in response.districts!!){
                content += "Name: " + e.name + "\n"
//                content += "Code: " + e.county + "\n"
//                content += "Count: " + e.count + "\n"
//                content += "Fälle pro Woche: " + e.weekIncidence + "\n"
//                content += "Fälle pro 100k: " + e.casesPer100k + "\n"
//                content += "Tode: " + e.deaths + "\n\n"

//                textView.text = content
            }
        })

//        viewModel.statesDataLiveData.observe(this, Observer { response ->
//
//            lastUpdateView.append(response.lastUpdate.toString())
//
//            textView.append(response.states!!.size.toString())
//
//            var content = " "
//
//            for(e in response.states!!){
//                content += "Name: " + e.name + "\n"
//                content += "Code: " + e.code + "\n"
//                content += "Count: " + e.count + "\n"
//                content += "Fälle pro Woche: " + e.weekIncidence + "\n"
//                content += "Fälle pro 100k: " + e.casesPer100k + "\n"
//                content += "Tode: " + e.deaths + "\n\n"
//
//                textView.append(content)
//            }
//
//        })

    }
}