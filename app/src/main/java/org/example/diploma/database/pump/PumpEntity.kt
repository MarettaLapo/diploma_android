package org.example.diploma.database.pump

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Update

@Entity(tableName = "pumps")
data class PumpEntity (

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val pumpId: Long?,

    //"Pump duration, us="
    val tp: Double?,

    //"Pump power, W="
    val wp: Double?,

    //"Pump coupling optics efficiency="
    val hc: Double?,

    //"Pump reflectivity="
    val rp: Double?,

    //"Pump wavelength, nm="
    val lp: Double?,

    //"Pump scheme type="(End|Side)
    @ColumnInfo(name = "scheme_id")
    val scheme: Int?,

    //"Pump type="(Pulsed pump|CW pump)
    @ColumnInfo(name = "ptype_id")
    val ptype: Int?,

    //"Pump forma type="(Rectangular|Trapeze|Varying)
    @ColumnInfo(name = "pform_id")
    val pform: Int?,

    //Для трапеции
    //"t1, us=   "

    val t1p: Double?,
    //"t2, us=   "
    val t2p: Double?

    //Varying(pformt)
)
