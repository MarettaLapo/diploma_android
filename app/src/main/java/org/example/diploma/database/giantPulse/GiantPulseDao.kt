package org.example.diploma.database.giantPulse

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface GiantPulseDao {
    @Query("Select * from giantPulse where id ==:giantPulseId")
    fun getGiantPulseData(giantPulseId: Long?): Flow<GiantPulseEntity>
}