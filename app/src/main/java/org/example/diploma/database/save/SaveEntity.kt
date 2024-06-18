package org.example.diploma.database.save

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.Index
import androidx.room.PrimaryKey
import org.example.diploma.database.amplifier.AmplifierEntity
import org.example.diploma.database.configuration.ConfigurationEntity
import org.example.diploma.database.laserMedium.LaserMediumEntity
import org.example.diploma.database.optimization.OptimizationEntity
import org.example.diploma.database.pump.PumpEntity
import org.example.diploma.database.qSwitch.QSwitchEntity

@Entity(
    tableName = "saves",
    indices = [Index("id")],
    foreignKeys = [
        ForeignKey(
            entity = LaserMediumEntity::class,
            parentColumns = ["id"],
            childColumns = ["laser_medium_id"],
            onDelete = CASCADE
        ),
        ForeignKey(
            entity = PumpEntity::class,
            parentColumns = ["id"],
            childColumns = ["pump_id"],
            onDelete = CASCADE
        ),
        ForeignKey(
            entity = ConfigurationEntity::class,
            parentColumns = ["id"],
            childColumns = ["configuration_id"],
            onDelete = CASCADE
        ),
        ForeignKey(
            entity = QSwitchEntity::class,
            parentColumns = ["id"],
            childColumns = ["q_switch_id"],
            onDelete = CASCADE
        ),
        ForeignKey(
            entity = AmplifierEntity::class,
            parentColumns = ["id"],
            childColumns = ["amplifier_id"],
            onDelete = CASCADE
        )
    ]
)

data class SaveEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val saveId: Long?,

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

    @ColumnInfo(name = "host") val host: String?,
    @ColumnInfo(name = "type") val type: String?,
    @ColumnInfo(name = "date") val date: String?,
)