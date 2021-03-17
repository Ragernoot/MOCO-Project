package com.example.testforcoronaapp

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.testforcoronaapp.repository.Repository
import com.example.testforcoronaapp.viewmodelfactory.MainViewModelFactory
import com.example.testforcoronaapp.viewmodels.DataServiceViewModel


class HomeFragment : Fragment() {

    private val TAG = "HomeFragment"
    private val DISTRICT_STRING = "District"
    private val STATE_STRING = "State"

    private lateinit var fragmentContext: Context
    private lateinit var viewModel: DataServiceViewModel
    private lateinit var dropDownStates : Spinner
    private lateinit var dropDownDistricts : Spinner
    private lateinit var textViewShowDistrict : TextView
    private lateinit var textViewShowState : TextView
    private lateinit var stateNames: MutableList<String>
    private var districtNames: MutableList<String> = mutableListOf()
    private lateinit var districtString : String
    private lateinit var stateString : String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        if(savedInstanceState != null){
            districtString = savedInstanceState.getString(DISTRICT_STRING).toString()
            stateString = savedInstanceState.getString(STATE_STRING).toString()
        }

        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)


        viewModel = ViewModelProvider(this, viewModelFactory).get(DataServiceViewModel::class.java)
        viewModel.getServiceDataViewModel()

        viewModel.statesDataLiveData.observe(this, Observer { stateResponse ->



            stateNames = stateResponse.map{ it.name }.toMutableList()
            val statesAdapter = ArrayAdapter<String>(fragmentContext, android.R.layout.simple_spinner_dropdown_item , stateNames)
            stateNames.add(0, "Bitte Bundesland wählen")
            dropDownStates.setSelection(0)
            dropDownStates.adapter = statesAdapter

            dropDownStates.onItemSelectedListener = object : OnItemSelectedListener {
                override fun onItemSelected(parentView: AdapterView<*>?, selectedItemView: View, position: Int, id: Long) {

                    val selectState = stateResponse.find { statesData -> statesData.name == dropDownStates.selectedItem }
                    stateString = ""
                    stateString += "Name: " + selectState?.name + "\n"
                    stateString += "Bevölkerung: " + selectState?.population + "\n"
                    stateString += "Fälle: " + selectState?.cases + "\n"
                    stateString += "Todesfälle: " + selectState?.deaths + "\n"
                    stateString += "Fälle pro 100k Einwohner: " + selectState?.casesPer100k + "\n"
                    stateString += "Fälle der letzten 7 Tage: " + selectState?.casesPerWeek + "\n"
                    stateString += "Todesfälle der letzten 7 Tage: " + selectState?.deathsPerWeek + "\n"
                    stateString += "Wochen Inzidenzwert: " + selectState?.weekIncidence + "\n"
                    textViewShowState.text = stateString

                    viewModel.districtDataLiveData.observe(viewLifecycleOwner, Observer { districtResponse ->


                        districtNames = districtResponse.filter{ it.state == dropDownStates.selectedItem }.map { it.name }.sortedBy { it }.toMutableList()

                        Log.e("districtNames ", districtNames.size.toString())
                        val districtAdapter = ArrayAdapter<String>(fragmentContext, android.R.layout.simple_spinner_dropdown_item, districtNames)
                        districtNames.add(0, "Bitte Landkreis/Stadt wählen")
                        dropDownDistricts.setSelection(0)
                        dropDownDistricts.adapter = districtAdapter

                        dropDownDistricts.onItemSelectedListener = object : OnItemSelectedListener {
                            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                                val selectedDistrict = districtResponse.find { districtData -> districtData.name == dropDownDistricts.selectedItem }
                                districtString = ""
                                districtString += "Name: " + selectedDistrict?.name + "\n"
                                districtString += "Bevölkerung: " + selectedDistrict?.population + "\n"
                                districtString += "Fälle: " + selectedDistrict?.cases + "\n"
                                districtString += "Todesfälle: " + selectedDistrict?.deaths + "\n"
                                districtString += "Fälle pro 100k Einwohner: " + selectedDistrict?.casesPer100k + "\n"
                                districtString += "Fälle der letzten 7 Tage: " + selectedDistrict?.casesPerWeek + "\n"
                                districtString += "Todesfälle der letzten 7 Tage: " + selectedDistrict?.deathsPerWeek + "\n"
                                districtString += "Wochen Inzidenzwert: " + selectedDistrict?.weekIncidence + "\n"
                                textViewShowDistrict.text = districtString

                            }

                            override fun onNothingSelected(parent: AdapterView<*>?) {
                                return
                            }
                        }


                    })
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    return
                }
            }

        })

    }
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView =  inflater.inflate(R.layout.fragment_home, container, false)

        fragmentContext = rootView.context

        dropDownStates = rootView.findViewById(R.id.dropDown_States)
        dropDownDistricts = rootView.findViewById(R.id.dropDown_DistrictsForState)
        textViewShowDistrict = rootView.findViewById(R.id.text_view_show_district)
        textViewShowState = rootView.findViewById(R.id.text_view_show_state)

        return rootView

    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        Log.d(TAG, "onSaveInstanceState: LLLLLLLLLLLLL")
        Log.d(TAG, "onSaveInstanceState: $stateString")
        Log.d(TAG, "onSaveInstanceState: $districtString")

        outState.run {
            putString(STATE_STRING, stateString)
            putString(DISTRICT_STRING, districtString)
        }
    }



}

