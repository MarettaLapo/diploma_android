package org.example.diploma.database.laserOutput

import kotlinx.coroutines.flow.Flow

class LaserOutputRepository(private val laserOutputDao: LaserOutputDao) {

    fun getLaserOutputData(laserOutputId: Long?): Flow<LaserOutputEntity> =
        laserOutputDao.getLaserOutputData(laserOutputId)
      fun getLaser(): Flow<LaserOutputEntity> =
        laserOutputDao.getLaser()
}