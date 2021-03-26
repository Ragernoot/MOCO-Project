package com.example.testforcoronaapp.model.room.district

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.testforcoronaapp.model.room.district.DistrictData
import com.example.testforcoronaapp.model.room.state.StatesData

@Dao
interface DistrictDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg districts : DistrictData)

//    @Insert
//    suspend fun insertAll(vararg states : Array<DistrictData>)
//
//    @Delete
//    suspend fun deleteAll(vararg districts : DistrictData)
//
    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(vararg district: DistrictData)

    @get:Query("SELECT * FROM district ORDER BY name")
    val allDistricts: Array<DistrictData>

    @Query("SELECT * FROM district ORDER BY name")
    suspend fun funGetAllDistricts() : Array<DistrictData>

    @Query("SELECT * FROM district WHERE district.ags == :ags")
    fun findDistrict(ags : String) : DistrictData


}
