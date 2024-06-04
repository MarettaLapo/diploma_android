package org.example.diploma.database.laserOutput

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import org.example.diploma.database.giantPulse.GiantPulseEntity

@Dao
interface LaserOutputDao {

    @Query("Select * from laserOutput where id ==:laserOutputId")
    fun getLaserOutputData(laserOutputId: Long?): Flow<LaserOutputEntity>

    @Query("Select * from laserOutput where id == 1")
    fun getLaser(): Flow<LaserOutputEntity>

}