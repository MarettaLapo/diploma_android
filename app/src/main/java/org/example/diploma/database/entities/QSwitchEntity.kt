package org.example.diploma.database.entities

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "qswitches")
data class QSwitchEntity (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "q_switch_id")
    val qSwitchId: Long,

    val isQSwitch: Boolean,

    //"PQS is installed"(passive)
    val isPQS: Boolean?,

    //"PQS thickness, cm"
    val lpsh: Double?,

    //"PQS refractive index"
    val npsh: Double,

    //"PQS initial transmission"
    val spT0: Double,

    //"PQS transmission caused by nonresonant losses"
    val spTD: Double,

    //"PQS decay time, us"
    val spt: Double,

    //"PQS cross-section, cm2"
    val ssh: Double,

    //"PQS FOM"
    val FOM: Double,



    //"AQS is installed"(active)
    val isAQS: Boolean?,

    //"AQS thickness, cm"
    val Lsh: Double?,

    //"AQS refractive index"
    val nsh: Double,

    //"AQS initial transmission="
    val sT0: Double,

    //"Max transmission="
    val sTmax: Double,

    //"Q-switch on delay, us="
    val sts: Double,

    //"Switch on time, us"
    val sTf: Double,

    //"Off Time, us="
    val sToff: Double,

    //"AQS type="
    val AQStype: String,

    //"Leading front type="
    val sFrontType: String,

    //"Mode="
    val mode: String,

    //"Frequency, kHz="
    val sf: Double,

    //"Off Time k="
    val sk: Double,

    //"Off Time b="
    val sb: Double,
)
{
    @Dao
    interface QSwitchDao{

    }
}