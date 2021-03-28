package com.example.testforcoronaapp.model.room.state

import androidx.room.*


@Dao
interface StateDAO {

    // Insert
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg state : StatesData)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWithoutCoroutine(vararg state : StatesData)

    // Update
    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(vararg state: StatesData)
    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateWithoutCoroutine(vararg state: StatesData)

    // Alle States
    @get:Query("SELECT * FROM state ORDER BY name")
    val allStates: Array<StatesData>
    @Query("SELECT * FROM state ORDER BY name")
    suspend fun funGetAllStates() : Array<StatesData>

    // Ein State
    @Query("SELECT * FROM state WHERE state.name == :name")
    fun getOneState(name : String) : StatesData

    // Leer?
    @Query("SELECT count(*) FROM state")
    fun isEmpty() : Int

}