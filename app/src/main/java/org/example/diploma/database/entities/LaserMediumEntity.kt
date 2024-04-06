package org.example.diploma.database.entities

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "laser_mediums")
data class LaserMediumEntity (

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "laser_medium_id")
    val laserMediumId: Long,

    //"Operation temperature, K="
    val OT: Double,

    //"Concentration Er, cm^-3"
    val Ner: String,

    //"Concentration Nd, cm^-3"
    val Nd: String,

    //"Concentration Yb, cm^-3"
    val Nyb: String,

    //"Tau 31 (Er), us="
    val T31: Double,

    //"Tau 32 (Er), us="
    val T32: Double,

    //"Tau 21 (Er), us="
    val T21: Double,

    //"Tau 4–1, us="
    val T41: Double,

    //"Tau 5–1, us="
    val T51: Double,

    //"Tau 5–4, us="
    val T54: Double,

    //"Tau 43, us="
    val T43: Double,

    //"Tau 31 (Yb), us="
    val T31Yb: Double,

    //"Transfer coefficient, cm^3 s^-1="
    val K: Double,

    //"Back transfer coefficient, a.u.="
    val A: Double,

    //"Initial 2-level concentration="
    val n2: Double,

    //"Initial 3-level concentration="
    val n3: Double,

    //"Initial 4-level concentration="
    val n4: Double,

    //"Absorption coefficient, cm^-1="
    val ac: Double,

    //"Excited state absorption cross section for lasing wavelengh, cm^2="
    val sa_wi: Double,

    //"Excited state absorption cross section for pump wavelengh, cm^2="
    val sa_wip: Double,

    //"Lasing wavelength, nm="
    val l0: Double,

    //"Degeneracy factor="
    val m: Double,

    //"Active element refractive index="
    val ne: Double,

    //"Stimulated emission cross-section, cm^2="
    val se: Double,

    //"Peak absorption cross-section, cm^2="
    val s0p: Double,

    //q
    val q: Double,

    //q1
    val q1: Double,

    //"Excitation quantum yield="
    val HL: Double,

    //"Branching of luminescence="
    val lb: Double,

    //"Temperature, K="
    val T: Double,

    //"1 Atomic percent="
    val AP: Double,

    //"Concentration of sensitizer ion, cm^-3="
    val Nsion: Double,

    //"Concentration of working ion, cm^-3="
    //"Concentration Yb, cm^-3="
    val Nwion: Double,

    //"Energy N2, cm^-1="
    val E2w: Double,

    //"Energy N3, cm^-1="
    val E3w: Double,

    //"Energy N4, cm^-1="
    val E4w: Double,

    //"Energy N2', cm^-1="
    val E2s: Double,

    //"Lasing scheme="
    val levels: Double,

    //"Sensitizing="
    val sensitizer: Double,
    )
{
    @Dao
    interface LaserMediumDao{

    }
}