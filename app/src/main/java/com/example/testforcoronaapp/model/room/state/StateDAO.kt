package com.example.testforcoronaapp.model.room.state

import androidx.room.*


@Dao
interface StateDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg state : StatesData)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTest(vararg state : StatesData)

//    @Insert
//    suspend fun insertAll(vararg states : Array<StatesData>)

//    @Delete
//    suspend fun deleteAll(vararg states : StatesData)
//
    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(vararg state: StatesData)

    @get:Query("SELECT * FROM state ORDER BY name")
    val allStates: Array<StatesData>

    @Query("SELECT * FROM state ORDER BY name")
    suspend fun funGetAllStates() : Array<StatesData>

}