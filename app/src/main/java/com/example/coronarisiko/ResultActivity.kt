package com.example.coronarisiko

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.coronarisiko.viewModelFactorys.ResultViewModelFactory
import com.example.coronarisiko.viewmodels.ResultViewModel

class ResultActivity : AppCompatActivity() {
    private lateinit var lastView : TextView
    private lateinit var currView : TextView

    private lateinit var viewModel : ResultViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.result_activity)

        lastView = findViewById(R.id.last_view)
        currView = findViewById(R.id.curr_view)

        val viewModelFactory = ResultViewModelFactory(application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(ResultViewModel::class.java)
        viewModel.getData()

        viewModel.lastDistrict.observe(this, Observer {

            var contentLast = ""
            contentLast += "Name: " + "${it?.name} \n" ?: "ff"
            contentLast += "Bevölkerung: " + it?.population + "\n"
            contentLast += "Fälle: " + it?.cases + "\n"
            contentLast += "Todesfälle: " + it?.deaths + "\n"
            contentLast += "Fälle pro 100k Einwohner: " + it?.casesPer100k + "\n"
            contentLast += "Fälle der letzten 7 Tage: " + it?.casesPerWeek + "\n"
            contentLast += "Todesfälle der letzten 7 Tage: " + it?.deathsPerWeek + "\n"
            contentLast += "Wochen Inzidenzwert: " + it?.weekIncidence + "\n"

            lastView.text = contentLast
        })

        viewModel.currentDistrict.observe(this, Observer {

            var contentCur = ""
            contentCur += "Name: " + "${it?.name} \n" ?: "ff"
            contentCur += "Bevölkerung: " + it?.population + "\n"
            contentCur += "Fälle: " + it?.cases + "\n"
            contentCur += "Todesfälle: " + it?.deaths + "\n"
            contentCur += "Fälle pro 100k Einwohner: " + it?.casesPer100k + "\n"
            contentCur += "Fälle der letzten 7 Tage: " + it?.casesPerWeek + "\n"
            contentCur += "Todesfälle der letzten 7 Tage: " + it?.deathsPerWeek + "\n"
            contentCur += "Wochen Inzidenzwert: " + it?.weekIncidence + "\n"

            currView.text = contentCur
        })
    }

}