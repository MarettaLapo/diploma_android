package org.example.diploma.database.qSwitch

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import org.example.diploma.database.optimization.OptimizationEntity
import org.example.diploma.database.pump.PumpEntity

@Dao
interface QSwitchDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(qSwitch: QSwitchEntity)

    @Delete
    suspend fun delete(qSwitch: QSwitchEntity)

    @Update
    suspend fun update(qSwitch: QSwitchEntity)

    @Query("Select * from qSwitches where id ==:qSwitchId")
    fun getQSwitchData(qSwitchId: Long?): Flow<QSwitchEntity>
}