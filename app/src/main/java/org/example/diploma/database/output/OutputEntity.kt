package org.example.diploma.database.output

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import org.example.diploma.database.amplifier.AmplifierEntity
import org.example.diploma.database.configuration.ConfigurationEntity
import org.example.diploma.database.giantPulse.GiantPulseEntity
import org.example.diploma.database.laserMedium.LaserMediumEntity
import org.example.diploma.database.laserOutput.LaserOutputEntity
import org.example.diploma.database.pump.PumpEntity
import org.example.diploma.database.qSwitch.QSwitchEntity

@Entity(
    tableName = "output",
    indices = [Index("id")],
    foreignKeys = [
        ForeignKey(
            entity = LaserOutputEntity::class,
            parentColumns = ["id"],
            childColumns = ["laser_output_id"]
        ),
        ForeignKey(
            entity = GiantPulseEntity::class,
            parentColumns = ["id"],
            childColumns = ["giant_pulse_id"]
        )
    ]
)
data class OutputEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val outputId: Long?,

    val type: String?,

    @ColumnInfo(name = "laser_output_id")
    val laserOutputId: Long?,

    @ColumnInfo(name = "giant_pulse_id")
    val giantPulseId: Long?,

    val is_cylinder: Int?,
    val scheme_type: Int?,
    val pump_type: Int?,
    val shutter: Int?,

    val u: String?,
    val loss: String?,
    val tsh: String?,
    val pump: String?,
    val output_power: String?,

    val del_sout: Double?,
    val del_u: Double?,
    val del_loss: Double?,
    val del_tsh: Double?,
    val del_p: Double?,
)
