package org.example.diploma.database.optimization

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import org.example.diploma.database.amplifier.AmplifierEntity
import org.example.diploma.database.pump.PumpEntity

@Dao
interface OptimizationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(optimization: OptimizationEntity)

    @Delete
    suspend fun delete(optimization: OptimizationEntity)

    @Update
    suspend fun update(optimization: OptimizationEntity)

    @Query("Select * from optimizations where id = 1")
    fun getInitialOptimization(): LiveData<OptimizationEntity>

    @Query("Select * from optimizations where id ==:optimizationId")
    fun getOptimizationData(optimizationId: Long): LiveData<OptimizationEntity>
}