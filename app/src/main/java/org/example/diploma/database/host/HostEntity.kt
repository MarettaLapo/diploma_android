package org.example.diploma.database.host

import androidx.databinding.Bindable
import androidx.lifecycle.LiveData
import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Update
import org.example.diploma.database.amplifier.AmplifierEntity
import org.example.diploma.database.configuration.ConfigurationEntity
import org.example.diploma.database.qSwitch.QSwitchEntity
import org.example.diploma.database.laserMedium.LaserMediumEntity
import org.example.diploma.database.optimization.OptimizationEntity
import org.example.diploma.database.pump.PumpEntity

@Entity(
    tableName = "hosts",
    indices = [Index("id")],
    foreignKeys = [
        ForeignKey(
            entity = LaserMediumEntity::class,
            parentColumns = ["id"],
            childColumns = ["laser_medium_id"]
        ),
        ForeignKey(
            entity = PumpEntity::class,
            parentColumns = ["id"],
            childColumns = ["pump_id"]
        ),
        ForeignKey(
            entity = ConfigurationEntity::class,
            parentColumns = ["id"],
            childColumns = ["configuration_id"]
        ),
        ForeignKey(
            entity = QSwitchEntity::class,
            parentColumns = ["id"],
            childColumns = ["q_switch_id"]
        )
        ,
        ForeignKey(
            entity = AmplifierEntity::class,
            parentColumns = ["id"],
            childColumns = ["amplifier_id"]
        )
        ,
        ForeignKey(
            entity = OptimizationEntity::class,
            parentColumns = ["id"],
            childColumns = ["optimization_id"]
        )
    ]
)

data class HostEntity (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val hostId: Long?,

    val host: String?,
    val type: String?,

    @ColumnInfo(name = "laser_medium_id")
    val laserMediumId: Long?,

    @ColumnInfo(name = "pump_id")
    val pumpId: Long?,

    @ColumnInfo(name = "q_switch_id")
    val qSwitchId: Long?,

    @ColumnInfo(name = "configuration_id")
    val configurationId: Long?,

    @ColumnInfo(name = "amplifier_id")
    val amplifierId: Long?,

    @ColumnInfo(name = "optimization_id")
    val optimizationId: Long?,
)
{
//    constructor() : this(null,null,null,null,null,
//        null,null,null,null)
}

