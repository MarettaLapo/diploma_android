package org.example.diploma.database.giantPulse

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow

class GiantPulseRepository(private val giantPulseDao: GiantPulseDao) {

    fun getGiantPulseData(giantPulseId: Long?): Flow<GiantPulseEntity> =
        giantPulseDao.getGiantPulseData(giantPulseId)
}