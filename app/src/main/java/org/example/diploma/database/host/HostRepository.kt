package org.example.diploma.database.host

import androidx.lifecycle.LiveData
import org.example.diploma.database.amplifier.AmplifierEntity

class HostRepository(private val hostDao: HostDao) {

    fun getHostData(hostId: Long): LiveData<HostEntity> = hostDao.getHostData(hostId)

    fun getAllHosts(): LiveData<List<HostEntity>> = hostDao.getAllHosts()



    suspend fun insert(host: HostEntity) {
        hostDao.insert(host)
    }

    suspend fun delete(host: HostEntity){
        hostDao.delete(host)
    }
    suspend fun update(host: HostEntity){
        hostDao.update(host)
    }
}