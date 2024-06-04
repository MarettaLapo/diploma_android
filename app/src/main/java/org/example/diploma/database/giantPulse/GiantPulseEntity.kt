package org.example.diploma.database.giantPulse

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "giantPulse")
data class GiantPulseEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val giantPulseId: Long?,

    val tm: Double?,
    val tg: Double?,
    val eqg: Double?,

    val hq: Double?,
    val est: Double?,
    val eqg2: Double?,

    val contrast: Double?,


)