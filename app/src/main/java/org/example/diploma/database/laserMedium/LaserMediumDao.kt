package org.example.diploma.database.laserMedium

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import org.example.diploma.database.amplifier.AmplifierEntity
import org.example.diploma.database.host.HostEntity


@Dao
interface LaserMediumDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(laserMedium: LaserMediumEntity)

    @Delete
    suspend fun delete(laserMedium: LaserMediumEntity)

    @Update
    suspend fun update(laserMedium: LaserMediumEntity)

    @Query("Select * from laserMediums where id ==:laserMediumId")
    fun getLaserMediumData(laserMediumId: Long?): Flow<LaserMediumEntity>

}