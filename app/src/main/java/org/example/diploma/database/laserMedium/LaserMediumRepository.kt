package org.example.diploma.database.laserMedium

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import org.example.diploma.database.amplifier.AmplifierEntity

class LaserMediumRepository(private val laserMediumDao: LaserMediumDao) {

    fun getLaserMediumData(laserMediumId: Long?): Flow<LaserMediumEntity> = laserMediumDao.getLaserMediumData(laserMediumId)
    suspend fun insert(laserMedium: LaserMediumEntity) {
        laserMediumDao.insert(laserMedium)
    }

    suspend fun delete(laserMedium: LaserMediumEntity){
        laserMediumDao.delete(laserMedium)
    }
    suspend fun update(laserMedium: LaserMediumEntity){
        laserMediumDao.update(laserMedium)
    }
}