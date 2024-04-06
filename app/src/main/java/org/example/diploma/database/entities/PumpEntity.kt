package org.example.diploma.database.entities

import androidx.lifecycle.LiveData
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
    @ColumnInfo(name = "pump_id")
    val pumpId: Long,

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
    val scheme: String?,

    //"Pump type="(Pulsed pump|CW pump)
    val ptype: String?,

    //"Pump forma type="(Rectangular|Trapeze|Varying)
    val pform: String?,

    //Для трапеции
    //"t1, us=   "

    val t1p: Double?,
    //"t2, us=   "
    val t2p: Double?

    //Varying(pformt)
)
{
    @Dao
    interface PumpDao{

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        suspend fun insertPump(pump :PumpEntity)

        // deletes an event
        @Delete
        suspend fun deletePump(pump: PumpEntity)

        // updates an event.
        @Update
        suspend fun updatePump(pump: PumpEntity)

        // read all the events from eventTable
        // and arrange events in ascending order
        // of their ids
        @Query("Select * from pumps")
        fun getAllPumps(): LiveData<List<PumpEntity>>
    }
}