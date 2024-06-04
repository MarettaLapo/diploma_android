package org.example.diploma.database.output

import kotlinx.coroutines.flow.Flow
import org.example.diploma.database.laserOutput.LaserOutputDao
import org.example.diploma.database.laserOutput.LaserOutputEntity

class OutputRepository(private val outputDao: OutputDao) {

    fun getOutputData(type: String?, isCylinder: Int?, schemeType: Int?, pumpType: Int?, shutter: Int?): Flow<OutputEntity> =
        outputDao.getOutputData(type, isCylinder, schemeType, pumpType, shutter)
}