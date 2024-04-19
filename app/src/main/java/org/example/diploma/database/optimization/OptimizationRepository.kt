package org.example.diploma.database.optimization

import androidx.lifecycle.LiveData
import org.example.diploma.database.amplifier.AmplifierEntity

class OptimizationRepository(private val optimizationDao: OptimizationDao) {

    val initialOptimization: LiveData<OptimizationEntity> = optimizationDao.getInitialOptimization()

    fun getOptimizationData(optimizationId: Long?): LiveData<OptimizationEntity> =
        optimizationDao.getOptimizationData(optimizationId)

    suspend fun insert(optimization: OptimizationEntity) {
        optimizationDao.insert(optimization)
    }

    suspend fun delete(optimization: OptimizationEntity) {
        optimizationDao.delete(optimization)
    }

    suspend fun update(optimization: OptimizationEntity) {
        optimizationDao.update(optimization)
    }
}