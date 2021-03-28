package com.example.coronarisiko.model.room.lastDistrict

import androidx.room.*

@Dao
interface LastDistrictDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg lastDistrict: LastDistrict)

    @Query("SELECT * FROM last_district")
    fun getLastDistrict() : LastDistrict

    @Query("SELECT * FROM last_district")
    suspend fun getLastDistrictSuspend() : LastDistrict

    @Query("SELECT count(*) FROM last_district")
    fun isEmpty() : Int

    @Delete
    fun delete(vararg district: LastDistrict)

}