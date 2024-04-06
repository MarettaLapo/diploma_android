package org.example.diploma.database.entities

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "amplifiers")
data class AmplifierEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "amplifier_id")
    val amplifierId: Long,

    //Waveform
    @ColumnInfo(name = "amp_pulse_form_id") val ampPulseFormId: Int,

    //"Pulse width (ns):"
    @ColumnInfo(name = "amp_pulse_duration") val ampPulseDuration: Double,

    //"Pulse full energy (mJ):"
    @ColumnInfo(name = "amp_pulse_energy") val ampPulseEnergy: Double,

    //"Amplifier length (cm):"
    @ColumnInfo(name = "amp_length") val ampLength: Double,

    @ColumnInfo(name = "is_multipass") val isMultipass: Boolean
) {
    @Dao
    interface AmplifierDao {

    }
}