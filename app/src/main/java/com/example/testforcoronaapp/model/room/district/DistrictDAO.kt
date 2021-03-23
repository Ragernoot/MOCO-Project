package com.example.testforcoronaapp.model.room.district

import androidx.room.*
import com.example.testforcoronaapp.model.room.district.DistrictData

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

    @Query("Select * From district ORDER BY name")
    suspend fun loadAllDistricts () : Array<DistrictData>

}
