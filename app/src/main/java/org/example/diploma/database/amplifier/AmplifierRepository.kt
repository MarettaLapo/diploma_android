package org.example.diploma.database.amplifier

import androidx.lifecycle.LiveData
import org.example.diploma.database.configuration.ConfigurationEntity

class AmplifierRepository(private val amplifierDao: AmplifierDao) {

    val allAmplifiers: LiveData<List<AmplifierEntity>> = amplifierDao.getAll()

    fun getAmplifierData(amplifierId: Long?): LiveData<AmplifierEntity> =
        amplifierDao.getAmplifierData(amplifierId)

    suspend fun insert(amplifier: AmplifierEntity) {
        amplifierDao.insert(amplifier)
    }

    suspend fun delete(amplifier: AmplifierEntity) {
        amplifierDao.delete(amplifier)
    }

    suspend fun update(amplifier: AmplifierEntity) {
        amplifierDao.update(amplifier)
    }
}