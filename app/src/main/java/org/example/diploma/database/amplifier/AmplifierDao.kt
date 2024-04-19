package org.example.diploma.database.amplifier

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface AmplifierDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(amplifier: AmplifierEntity)

    @Delete
    suspend fun delete(amplifier: AmplifierEntity)

    @Update
    suspend fun update(amplifier: AmplifierEntity)

    @Query("Select * from amplifiers")
    fun getAll(): LiveData<List<AmplifierEntity>>

    @Query("Select * from amplifiers where id ==:amplifierId")
    fun getAmplifierData(amplifierId: Long?): LiveData<AmplifierEntity>
}