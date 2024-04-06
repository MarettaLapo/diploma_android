package org.example.diploma.database.entities

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

@Entity(
    tableName = "hosts",
    indices = [Index("host_id")],
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
        )
    ]
)

data class HostEntity (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "host_id")
    val hostId: Long,

    val host: String,
    val type: String,

    @ColumnInfo(name = "laser_medium_id")
    val laserMediumId: Long?,

    @ColumnInfo(name = "pump_id")
    val pumpId: Long?,

    @ColumnInfo(name = "q_switch_id")
    val qSwitchId: Long?,

    @ColumnInfo(name = "configuration_id")
    val optimizationId: Long?,
)
{
    @Dao
    interface HostDao{
        @Insert(onConflict = OnConflictStrategy.REPLACE)
        suspend fun insertHost(host :HostEntity)

        // deletes an event
        @Delete
        suspend fun deleteHost(host: HostEntity)

        // updates an event.
        @Update
        suspend fun updateHost(host: HostEntity)

        // read all the events from eventTable
        // and arrange events in ascending order
        // of their ids
        @Query("Select * from hosts")
        fun getAllHosts(): LiveData<List<HostEntity>>
    }
}
