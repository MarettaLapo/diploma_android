package org.example.diploma.database.configuration

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "configurations")
data class ConfigurationEntity (

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val configurationId: Long?,

    @ColumnInfo(name = "is_cylinder")
    val isCylinder: Boolean?,

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
    ){
//    constructor() : this(null,null,null,null,null,
//        null,null,null,null,null,null)

    //Lasing area, cm^2
    @Ignore var ag: Double? = null

    //Telescope magnification
    @Ignore var tm: Double? = 1.0
}
