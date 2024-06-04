package org.example.diploma.database.pump

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import org.example.diploma.database.amplifier.AmplifierEntity
import org.example.diploma.database.pump.PumpEntity

class PumpRepository(private val pumpDao: PumpDao) {

    fun getPumpData(pumpId: Long?): Flow<PumpEntity> = pumpDao.getPumpData(pumpId)
    suspend fun insert(pump: PumpEntity) : Long{
        return pumpDao.insert(pump)
    }

    suspend fun delete(pump: PumpEntity){
        pumpDao.delete(pump)
    }
    suspend fun update(pump: PumpEntity){
        pumpDao.update(pump)
    }

}