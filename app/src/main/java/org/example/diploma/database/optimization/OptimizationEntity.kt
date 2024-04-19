package org.example.diploma.database.optimization

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "optimizations")
data class OptimizationEntity (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val optimizationId: Long?,
    @ColumnInfo(name = "value_id") val valueId: Int?,
    @ColumnInfo(name = "depends_id") val dependsId: Int?,
    @ColumnInfo(name = "range_start") val rangeStart: Double?,
    @ColumnInfo(name = "range_end") val rangeEnd: Double?,
    @ColumnInfo(name = "number_of_points") val numberOfPoints: Int?,
    @ColumnInfo(name = "is_use") val isUse: Boolean?,
)