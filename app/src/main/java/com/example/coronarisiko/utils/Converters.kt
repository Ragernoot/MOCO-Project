package com.example.coronarisiko.utils

import com.example.coronarisiko.model.room.currentDistrict.CurrentDistrict
import com.example.coronarisiko.model.room.district.DistrictData
import com.example.coronarisiko.model.room.lastDistrict.LastDistrict

class Converters {
    fun fromDistrictToLastDistrict(districtData: DistrictData) : LastDistrict{
        return districtData.let { LastDistrict(it.ags, it.name, it.county, it.state, it.population, it.cases, it.deaths, it.casesPerWeek, it.deathsPerWeek, it.stateAbbreviation, it.recovered, it.weekIncidence, it.casesPer100k) }
    }

    fun fromLastDistrictToDistrict(lastDistrict: LastDistrict) : DistrictData{
        return lastDistrict.let { DistrictData(it.ags, it.name, it.county, it.state, it.population, it.cases, it.deaths, it.casesPerWeek, it.deathsPerWeek, it.stateAbbreviation, it.recovered, it.weekIncidence, it.casesPer100k) }
    }

    fun fromDistrictToCurrentDistrict(districtData: DistrictData) : CurrentDistrict{
        return districtData.let { CurrentDistrict(it.ags, it.name, it.county, it.state, it.population, it.cases, it.deaths, it.casesPerWeek, it.deathsPerWeek, it.stateAbbreviation, it.recovered, it.weekIncidence, it.casesPer100k) }
    }

    fun fromCurrentDistrictToDistrict(currentDistrict: CurrentDistrict) : DistrictData{
        return currentDistrict.let { DistrictData(it.ags, it.name, it.county, it.state, it.population, it.cases, it.deaths, it.casesPerWeek, it.deathsPerWeek, it.stateAbbreviation, it.recovered, it.weekIncidence, it.casesPer100k) }
    }

}