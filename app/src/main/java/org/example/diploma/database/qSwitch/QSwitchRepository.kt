package org.example.diploma.database.qSwitch

import androidx.lifecycle.LiveData
import org.example.diploma.database.pump.PumpEntity

class QSwitchRepository(private val qSwitchDao: QSwitchDao) {

    fun getQSwitchData(qSwitchId: Long?): LiveData<QSwitchEntity> = qSwitchDao.getQSwitchData(qSwitchId)
    suspend fun insert(qSwitch: QSwitchEntity) {
        qSwitchDao.insert(qSwitch)
    }

    suspend fun delete(qSwitch: QSwitchEntity){
        qSwitchDao.delete(qSwitch)
    }
    suspend fun update(qSwitch: QSwitchEntity){
        qSwitchDao.update(qSwitch)
    }
}