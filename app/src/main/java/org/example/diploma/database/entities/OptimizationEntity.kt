package org.example.diploma.database.entities

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "optimizations")
data class OptimizationEntity (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "optimization_id")
    val optimizationId: Long,
    @ColumnInfo(name = "value_id") val valueId: Long,
    @ColumnInfo(name = "depends_id") val dependsId: Long,
    @ColumnInfo(name = "range_start") val rangeStart: Double,
    @ColumnInfo(name = "range_end") val rangeEnd: Double,
    @ColumnInfo(name = "number_of_points") val numberOfPoints: Int,
)
{
    @Dao
    interface OptimizationDao{

    }
}