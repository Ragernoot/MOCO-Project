package com.example.testforcoronaapp.model.room.state

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.testforcoronaapp.model.room.state.StatesData

@Dao
interface StateDAO {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg states : StatesData)

//    @Insert
//    suspend fun insertAll(vararg states : Array<StatesData>)

//    @Delete
//    suspend fun deleteAll(vararg states : StatesData)
//
    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(vararg state: StatesData)

    @get:Query("SELECT * FROM state ORDER BY name")
    val allStates: LiveData<Array<StatesData>>

}