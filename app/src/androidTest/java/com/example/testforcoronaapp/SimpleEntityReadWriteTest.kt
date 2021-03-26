package com.example.testforcoronaapp

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.testforcoronaapp.model.room.AppDatabase
import com.example.testforcoronaapp.model.room.district.DistrictDAO
import com.example.testforcoronaapp.model.room.state.StateDAO
import com.example.testforcoronaapp.model.room.state.StatesData
import org.hamcrest.CoreMatchers.equalTo
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class SimpleEntityReadWriteTest {
    private lateinit var stateDAO: StateDAO
    private lateinit var districtDAO: DistrictDAO
    private lateinit var db: AppDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context,
            AppDatabase::class.java
        ).build()
        stateDAO = db.stateDAO()
        districtDAO = db.districtDAO()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeUserAndReadInList() {
        val state = StatesData(name = "Test", abbreviation = "TE", cases = 24, casesPer100k = 24.33, casesPerWeek = 2,
            deaths = 1, deathsPerWeek = 14, id = 15, population = 199494, recovered = 1132, weekIncidence = 31.6654)
        stateDAO.insertTest(state)
        val first = stateDAO.allStates.get(0)
        assertThat(first, equalTo(state))

    }
}