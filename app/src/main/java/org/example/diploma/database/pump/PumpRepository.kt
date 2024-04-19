package org.example.diploma.database.pump

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import org.example.diploma.database.amplifier.AmplifierEntity
import org.example.diploma.database.pump.PumpEntity

class PumpRepository(private val pumpDao: PumpDao) {

    fun getPumpData(pumpId: Long?): LiveData<PumpEntity> = pumpDao.getPumpData(pumpId)
    suspend fun insert(pump: PumpEntity) {
        pumpDao.insert(pump)
    }

    suspend fun delete(pump: PumpEntity){
        pumpDao.delete(pump)
    }
    suspend fun update(pump: PumpEntity){
        pumpDao.update(pump)
    }

}