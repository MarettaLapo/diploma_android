package org.example.diploma.database.pump

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import org.example.diploma.database.laserMedium.LaserMediumEntity


@Dao
interface PumpDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(pump: PumpEntity) : Long

    @Delete
    suspend fun delete(pump: PumpEntity)

    @Update
    suspend fun update(pump: PumpEntity)

    @Query("Select * from pumps where id ==:pumpId")
    fun getPumpData(pumpId: Long?): Flow<PumpEntity>
}