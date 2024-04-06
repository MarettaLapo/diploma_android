package org.example.diploma.database.entities

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "configurations")
data class ConfigurationEntity (

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "configuration_id")
    val configurationId: Long,

    @ColumnInfo(name = "is_cylinder")
    val isCylinder: Boolean,

    //"Cavity length, cm"
    val lc: Double?,

    //"La, cm="
    val la: Double?,

    //"Ld, cm="
    val ld: Double?,

    //"Lb, cm="
    val lb: Double?,

    //"Reflectivity of output coupler="
    val roc: Double?,

    //"Active element dissipative losses, cm^-1="
    val ga: Double?,

    //"Intrinsic resonator losses="
    val gc: Double?,

    //"Overlap efficiency="
    val hov: Double?,

    //"DIA, cm="
    val dia: Double?,

    //мб не нужно всегда 1.0
    //"Telescope magnification=1.0"
    val tm: Double?,
    )
{
    @Dao
    interface ConfigurationDao{

    }
}