package org.example.diploma.database.entities

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "saves",
    indices = [Index("save_id")],
    foreignKeys = [
        ForeignKey(
            entity = LaserMediumEntity::class,
            parentColumns = ["laser_medium_id"],
            childColumns = ["laser_medium_id"]
        ),
        ForeignKey(
            entity = PumpEntity::class,
            parentColumns = ["pump_id"],
            childColumns = ["pump_id"]
        ),
        ForeignKey(
            entity = ConfigurationEntity::class,
            parentColumns = ["configuration_id"],
            childColumns = ["configuration_id"]
        ),
        ForeignKey(
            entity = QSwitchEntity::class,
            parentColumns = ["q_switch_id"],
            childColumns = ["q_switch_id"]
        ),
        ForeignKey(
            entity = OptimizationEntity::class,
            parentColumns = ["optimization_id"],
            childColumns = ["optimization_id"]
        ),
        ForeignKey(
            entity = AmplifierEntity::class,
            parentColumns = ["amplifier_id"],
            childColumns = ["amplifier_id"]
        )
    ]
)

data class SaveEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "save_id")
    val saveId: Long,

    @ColumnInfo(name = "laser_medium_id")
    val laserMediumId: Long?,

    @ColumnInfo(name = "pump_id")
    val pumpId: Long?,

    @ColumnInfo(name = "q_switch_id")
    val qSwitchId: Long?,

    @ColumnInfo(name = "optimization_id")
    val optimizationId: Long?,

    @ColumnInfo(name = "configuration_id")
    val configurationId: Long?,

    @ColumnInfo(name = "amplifier_id")
    val amplifierId: Long?,

    @ColumnInfo(name = "host") val host: String,
    @ColumnInfo(name = "type") val type: String,
    @ColumnInfo(name = "date") val date: String,
)
{
    @Dao
    interface SaveDao{

    }
}