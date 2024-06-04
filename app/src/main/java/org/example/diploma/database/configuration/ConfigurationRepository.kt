package org.example.diploma.database.configuration

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import org.example.diploma.database.amplifier.AmplifierEntity

class ConfigurationRepository(private val configurationDao: ConfigurationDao) {

    fun getConfigurationData(configurationId: Long?): Flow<ConfigurationEntity> = configurationDao.getConfigurationData(configurationId)
    suspend fun insert(configuration: ConfigurationEntity) : Long {
        return configurationDao.insert(configuration)
    }

    suspend fun delete(configuration: ConfigurationEntity){
        configurationDao.delete(configuration)
    }
    suspend fun update(configuration: ConfigurationEntity){
        configurationDao.update(configuration)
    }
}