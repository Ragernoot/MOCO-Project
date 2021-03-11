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
import com.example.testforcoronaapp.utils.Constants
import com.example.testforcoronaapp.viewmodelfactory.MainViewModelFactory
import com.example.testforcoronaapp.viewmodels.MainViewModel
import com.google.gson.Gson
import org.json.JSONObject
import java.lang.StringBuilder

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

            val repository = Repository()
            val viewModelFactory = MainViewModelFactory(repository)
            viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
            viewModel.getDistrictDataViewModel()

            Log.e("URL TEST", Constants.BASE_URL)

            viewModel.districtDataLiveData.observe(this, Observer { response ->

                if(response.isSuccessful) {
                    val jsonDataString = response.body()
                    val json = JSONObject(jsonDataString!!)
                    val data = json.getJSONObject("data")
                    val allKeys = data.keys()
                    val listOfJSONObject = mutableListOf<JSONObject>()
                    allKeys.forEach { listOfJSONObject.add(data.getJSONObject(it)) }

                    val gson = Gson()
                    val listOfJavaObject = mutableListOf<ActualData>()
                    for (listItem in listOfJSONObject) {
                        var jsonObjectString = listItem.toString()

                        val sb = StringBuilder(jsonObjectString)
                        val first: Int = jsonObjectString.indexOf("\"")
                        val last: Int = jsonObjectString.lastIndexOf("\"")
                        if (first != last) {
                            var i = first + 1
                            while (i < last) {
                                if (sb[i] == '\'') {
                                    sb.insert(i, '\'')
                                    i++
                                }
                                i++
                            }
                        }
                        jsonObjectString = sb.toString()

                        val actualData = gson.fromJson(jsonObjectString, ActualData::class.java)
                        listOfJavaObject.add(actualData)
                    }
                    Log.e("TEST UNTEN", listOfJavaObject.toString())
                } else {
                    Log.e("WrongMAP", "Something went Wrong")
                }
            })
        }
    }
}

