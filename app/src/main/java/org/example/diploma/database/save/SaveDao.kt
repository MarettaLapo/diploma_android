package org.example.diploma.database.save

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import org.example.diploma.database.host.HostEntity

@Dao
interface SaveDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(save: SaveEntity): Long

    @Delete
    suspend fun delete(save: SaveEntity)

    @Update
    suspend fun update(save: SaveEntity)

    @Query("Select * from saves where id ==:saveId")
    fun getSaveData(saveId: Long): LiveData<SaveEntity>

    @Query("SELECT * FROM saves")
    fun getAllSaves(): Flow<List<SaveEntity>>
}