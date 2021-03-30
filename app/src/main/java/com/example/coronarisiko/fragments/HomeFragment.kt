package com.example.coronarisiko.fragments

import android.content.ContentValues.TAG
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
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
import com.example.coronarisiko.R
import com.example.coronarisiko.viewModelFactorys.HomeViewModelFactory
import com.example.coronarisiko.viewmodels.HomeViewModel
import java.math.BigDecimal
import java.math.BigInteger
import java.math.RoundingMode
import java.text.DecimalFormat


class HomeFragment : Fragment() {

    private lateinit var fragmentContext: Context

    private lateinit var viewModel: HomeViewModel
    private lateinit var dropDownStates : Spinner
    private lateinit var dropDownDistricts : Spinner
    private lateinit var textViewShowDistrict : TextView
    private lateinit var textViewShowState : TextView
    private lateinit var stateNames: MutableList<String>
    private lateinit var districtCountyNames: MutableList<String>
    private lateinit var districtString : String
    private lateinit var stateString : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fragmentContext = activity!!.applicationContext

        val viewModelFactory = HomeViewModelFactory(activity!!.application)

        viewModel = ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)
        viewModel.getDataFromRoom()

        viewModel.stateDataLiveData.observe(this, Observer { stateResponse ->

            stateNames = stateResponse.map { it.name }.toMutableList()
            val statesAdapter = ArrayAdapter<String>(fragmentContext, android.R.layout.simple_spinner_dropdown_item, stateNames)
            stateNames.add(0, "Bundesland")
            dropDownStates.setSelection(0)
            dropDownStates.adapter = statesAdapter

            dropDownStates.onItemSelectedListener = object : OnItemSelectedListener {
                override fun onItemSelected(parentView: AdapterView<*>?, selectedItemView: View, position: Int, id: Long) {
                    val selectState = stateResponse.find { statesData -> statesData.name == dropDownStates.selectedItem }
                    stateString = "Bundesland Daten:" + "\n"
                    stateString += "Name: " + (selectState?.name ?: "") + "\n"
                    stateString += "Bevölkerung: " + (selectState?.population ?: "") + "\n"
                    stateString += "Corona-Fälle: " + (selectState?.cases ?: "") + "\n"
                    stateString += "Todesfälle: " + (selectState?.deaths ?: "") + "\n"
                    stateString += "Fälle pro 100k Einwohner: " + (if(selectState?.casesPer100k == null) "" else BigDecimal(selectState.casesPer100k).setScale(2, RoundingMode.HALF_EVEN)) + "\n"
                    stateString += "Fälle der letzten 7 Tage: " + (selectState?.casesPerWeek ?: "") + "\n"
                    stateString += "Todesfälle der letzten 7 Tage: " + (selectState?.deathsPerWeek ?: "") + "\n"
                    stateString += "Wochen Inzidenzwert: " + (if(selectState?.weekIncidence == null)  "" else BigDecimal(selectState.weekIncidence).setScale(2, RoundingMode.HALF_EVEN)) + "\n"
                    textViewShowState.text = stateString

                    viewModel.districtDataLiveData.observe(viewLifecycleOwner, Observer { districtResponse ->

                            districtCountyNames = districtResponse.filter { it.state == dropDownStates.selectedItem }.map { it.county }.sortedBy { it }.toMutableList()

                            val districtAdapter = ArrayAdapter<String>(fragmentContext, android.R.layout.simple_spinner_dropdown_item, districtCountyNames)
                            districtCountyNames.add(0, "Landkreis/Stadt")
                            dropDownDistricts.setSelection(0)
                            dropDownDistricts.adapter = districtAdapter

                            dropDownDistricts.onItemSelectedListener = object : OnItemSelectedListener {
                                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                                        val selectedDistrict = districtResponse.find { districtData -> districtData.county == dropDownDistricts.selectedItem }
                                        districtString = "Landkreis Daten: " + "\n"
                                        districtString += "Landkreis/Stadt: " + (selectedDistrict?.name ?: "") + "\n"
                                        districtString += "Bevölkerung: " + (selectedDistrict?.population ?: "") + "\n"
                                        districtString += "Corona-Fälle: " + (selectedDistrict?.cases ?: "") + "\n"
                                        districtString += "Todesfälle: " + (selectedDistrict?.deaths ?: "") + "\n"
                                        districtString += "Fälle pro 100k Einwohner: " + (if(selectedDistrict?.casesPer100k == null)  "" else BigDecimal(selectedDistrict.casesPer100k).setScale(2, RoundingMode.HALF_EVEN)) + "\n"
                                        districtString += "Fälle der letzten 7 Tage: " + (selectedDistrict?.casesPerWeek ?: "") + "\n"
                                        districtString += "Todesfälle der letzten 7 Tage: " + (selectedDistrict?.deathsPerWeek ?: "") + "\n"
                                        districtString += "Wochen Inzidenzwert: " + (if(selectedDistrict?.weekIncidence == null)  "" else BigDecimal(selectedDistrict.weekIncidence).setScale(2, RoundingMode.HALF_EVEN)) + "\n"
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

        dropDownStates = rootView.findViewById(R.id.dropDown_States)
        dropDownDistricts = rootView.findViewById(R.id.dropDown_DistrictsForState)
        textViewShowDistrict = rootView.findViewById(R.id.text_view_show_district)
        textViewShowState = rootView.findViewById(R.id.text_view_show_state)

        return rootView

    }
}

