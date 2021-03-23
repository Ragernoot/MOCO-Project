package com.example.testforcoronaapp.model.room.state

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

    @Query("Select * From state ORDER BY name")
    suspend fun loadAllStates () : Array<StatesData>

}