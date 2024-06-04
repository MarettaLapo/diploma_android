package org.example.diploma.database.laserOutput

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "laserOutput")
data class LaserOutputEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val laserOutputId: Long?,

    val imax: Double?,
    val efg: Double?,
    val hf: Double?,

    val tm: Double?,
    val aop: Double?,
)
