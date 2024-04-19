package org.example.diploma.database.host

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface HostDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(host: HostEntity)

    @Delete
    suspend fun delete(host: HostEntity)

    @Update
    suspend fun update(host: HostEntity)

    @Query("Select * from hosts where id ==:hostId")
    fun getHostData(hostId: Long): LiveData<HostEntity>

    @Query("Select * from hosts")
    fun getAllHosts(): LiveData<List<HostEntity>>
}