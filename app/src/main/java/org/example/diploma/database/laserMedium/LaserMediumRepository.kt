package org.example.diploma.database.laserMedium

import androidx.lifecycle.LiveData
import org.example.diploma.database.amplifier.AmplifierEntity

class LaserMediumRepository(private val laserMediumDao: LaserMediumDao) {

    fun getLaserMediumData(laserMediumId: Long?): LiveData<LaserMediumEntity> = laserMediumDao.getLaserMediumData(laserMediumId)
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