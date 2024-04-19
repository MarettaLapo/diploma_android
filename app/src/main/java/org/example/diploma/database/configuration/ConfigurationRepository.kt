package org.example.diploma.database.configuration

import androidx.lifecycle.LiveData
import org.example.diploma.database.amplifier.AmplifierEntity

class ConfigurationRepository(private val configurationDao: ConfigurationDao) {

    fun getConfigurationData(configurationId: Long?): LiveData<ConfigurationEntity> = configurationDao.getConfigurationData(configurationId)
    suspend fun insert(configuration: ConfigurationEntity) {
        configurationDao.insert(configuration)
    }

    suspend fun delete(configuration: ConfigurationEntity){
        configurationDao.delete(configuration)
    }
    suspend fun update(configuration: ConfigurationEntity){
        configurationDao.update(configuration)
    }
}