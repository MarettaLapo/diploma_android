package org.example.diploma.database.amplifier

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "amplifiers")
data class AmplifierEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val amplifierId: Long?,

    //"Amplifier length (cm):"
    @ColumnInfo(name = "amp_length") val ampLength: Double?,

    //"Pulse width (ns):"
    @ColumnInfo(name = "amp_pulse_duration") val ampPulseDuration: Double?,

    //"Pulse full energy (mJ):"
    @ColumnInfo(name = "amp_pulse_energy") val ampPulseEnergy: Double?,



    @ColumnInfo(name = "is_multipass") val isMultipass: Int?,

    //Waveform
    @ColumnInfo(name = "waveform") val waveform: Int?,
)