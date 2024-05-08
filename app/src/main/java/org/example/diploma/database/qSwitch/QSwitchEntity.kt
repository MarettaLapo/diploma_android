package org.example.diploma.database.qSwitch

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "qSwitches")
data class QSwitchEntity (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val qSwitchId: Long?,

    @ColumnInfo(name = "is_q_switch")
    val isQSwitch: Boolean?,

    //"PQS is installed"(passive)
    @ColumnInfo(name = "is_pqs")
    val isPQS: Boolean?,

    //"PQS thickness, cm"
    val lpsh: Double?,

    //"PQS refractive index"
    val npsh: Double?,

    //"PQS initial transmission"
    val spt0: Double?,

    //"PQS transmission caused by nonresonant losses"
    val sptd: Double?,

    //"PQS decay time, us"
    val spt: Double?,

    //"PQS cross-section, cm2"
    val ssh: String?,

    //"PQS FOM"
    val fom: Double?,



    //"AQS is installed"(active)
    @ColumnInfo(name = "is_aqs")
    val isAQS: Boolean?,

    //"AQS thickness, cm"
    val lsh: Double?,

    //"AQS refractive index"
    val nsh: Double?,

    //"AQS initial transmission="
    val st0: Double?,

    //"Max transmission="
    val stmax: Double?,

    //"Q-switch on delay, us="
    val sts: Double?,

    //"Switch on time, us"
    val stf: Double?,

    //"Off Time, us="
    val stoff: Double?,

    //"AQS type="
    @ColumnInfo(name = "aqs_type")
    val AQStype: Int?,

    //"Leading front type="
    @ColumnInfo(name = "s_front_type")
    val sFrontType: Int?,

    //"Mode="
    val mode: Int?,

    //"Frequency, kHz="
    val sf: Double?,

    //"Off Time k="
    val sk: Double?,

    //"Off Time b="
    val sb: Double?,

    @ColumnInfo(name = "abs_coef")
    val absCoef: Double?
)
//    constructor() : this(null,null,null,null,null,
//        null,null,null,null,null,null,null,null,
//        null,null,null, null, null, null,null,null,
//        null,null,null)
