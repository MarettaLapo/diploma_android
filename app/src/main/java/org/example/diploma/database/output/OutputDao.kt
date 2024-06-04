package org.example.diploma.database.output

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import org.example.diploma.database.laserOutput.LaserOutputEntity

@Dao
interface OutputDao {
    @Query("Select * from output where type ==:type and is_cylinder ==:isCylinder and scheme_type ==:schemeType and pump_type ==:pumpType and shutter ==:shutter")
    fun getOutputData(type: String?, isCylinder: Int?, schemeType: Int?, pumpType: Int?, shutter: Int?): Flow<OutputEntity>
}