package org.example.diploma.database.amplifier

import androidx.lifecycle.LiveData

class AmplifierRepository(private val amplifierDao: AmplifierDao) {

    val allAmplifiers: LiveData<List<AmplifierEntity>> = amplifierDao.getAll()

    val initialAmplifier: LiveData<AmplifierEntity> = amplifierDao.getInitialAmplifier()

    suspend fun insert(amplifier: AmplifierEntity) {
        amplifierDao.insert(amplifier)
    }

    suspend fun delete(amplifier: AmplifierEntity){
        amplifierDao.delete(amplifier)
    }
    suspend fun update(amplifier: AmplifierEntity){
        amplifierDao.update(amplifier)
    }
}