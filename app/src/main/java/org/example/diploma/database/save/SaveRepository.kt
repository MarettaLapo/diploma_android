package org.example.diploma.database.save

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow
import org.example.diploma.database.host.HostEntity
import org.example.diploma.database.qSwitch.QSwitchEntity

class SaveRepository(private val saveDao: SaveDao) {

    fun getSaveData(saveId: Long): LiveData<SaveEntity> = saveDao.getSaveData(saveId)

    fun getAllSaves(): Flow<List<SaveEntity>> = saveDao.getAllSaves()

    suspend fun insert(save: SaveEntity) {
        saveDao.insert(save)
    }

    suspend fun delete(save: SaveEntity){
        saveDao.delete(save)
    }
    suspend fun update(save: SaveEntity){
        saveDao.update(save)
    }
}