package com.example.coronarisiko.model.room.district

import androidx.room.*

@Dao
interface DistrictDAO {

    // Insert
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg districts : DistrictData)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWithoutCoroutine(vararg districts : DistrictData)

    // Update
    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(vararg district: DistrictData)
    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateWithoutCoroutine(vararg districtData: DistrictData)

    // Alle Districts
    @get:Query("SELECT * FROM district ORDER BY name")
    val allDistricts: Array<DistrictData>
    @Query("SELECT * FROM district ORDER BY name")
    suspend fun funGetAllDistricts() : Array<DistrictData>

    // Ein District
    @Query("SELECT * FROM district WHERE district.ags == :ags")
    fun findDistrict(ags : String) : DistrictData

    // Leer?
    @Query("SELECT count(*) FROM district")
    fun isEmpty() : Int

}
