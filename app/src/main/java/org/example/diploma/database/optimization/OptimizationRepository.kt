package org.example.diploma.database.optimization

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import org.example.diploma.database.amplifier.AmplifierEntity

class OptimizationRepository(private val optimizationDao: OptimizationDao) {

    val initialOptimization: LiveData<OptimizationEntity> = optimizationDao.getInitialOptimization()

    fun getOptimizationData(optimizationId: Long?): Flow<OptimizationEntity> =
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