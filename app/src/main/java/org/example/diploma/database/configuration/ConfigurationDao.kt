package org.example.diploma.database.configuration

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
interface ConfigurationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(configuration: ConfigurationEntity)

    @Delete
    suspend fun delete(configuration: ConfigurationEntity)

    @Update
    suspend fun update(configuration: ConfigurationEntity)

    @Query("Select * from configurations where id ==:configurationId")
    fun getConfigurationData(configurationId: Long?): Flow<ConfigurationEntity>

}