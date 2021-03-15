package com.example.testforcoronaapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.testforcoronaapp.model.districtModel.DistrictData
import com.example.testforcoronaapp.repository.Repository
import com.example.testforcoronaapp.viewmodelfactory.MainViewModelFactory
import com.example.testforcoronaapp.viewmodels.HomeFragmentViewModel
import com.google.gson.Gson
import org.json.JSONObject
import java.lang.StringBuilder

class HomeFragment : Fragment() {

    private lateinit var viewModel: HomeFragmentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)

        viewModel = ViewModelProvider(this, viewModelFactory).get(HomeFragmentViewModel::class.java)
        viewModel.getServiceDataViewModel()

        viewModel.districtDataLiveData.observe(this, Observer { response ->

            response.sortBy { it.name }
            val view = view
            if (view != null) {
                val textView = view.findViewById<TextView>(R.id.nav_home_textView)
                var content = ""
                var i = 1
                for(item in response){
                    content += i.toString() + ' ' + item.name + "\n"
                    textView.text = content
                    i++
                }

            }

        })

        viewModel.statesDataLiveData.observe(this, Observer { response ->

            val view = view
            if (view != null) {
                val textView = view.findViewById<TextView>(R.id.nav_home_textView)
                var content = ""
                var x = 1
                for(item in response){
                    content += x.toString() + ' ' + item.name + "\n"
                    textView.text = content
                    x++
                }

            }

        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

}

