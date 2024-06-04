package org.example.diploma.database.laserMedium

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Ignore

@Entity(tableName = "laserMediums")
data class LaserMediumEntity (

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val laserMediumId: Long?,

    val host: String?,
    val type: String?,

    //"Operation temperature, K="
    val ot: Double?,

    //"Concentration Er, cm^-3"
    val ner: String?,

    //"Concentration Nd, cm^-3"
    val nd: String?,

    //"Concentration Yb, cm^-3"
    val nyb: String?,

    //"Tau 31 (Er), us="
    val t31: Double?,

    //"Tau 32 (Er), us="
    val t32: Double?,

    //"Tau 21 (Er), us="
    val t21: Double?,

    //"Tau 4–1, us="
    val t41: Double?,

    //"Tau 5–1, us="
    val t51: Double?,

    //"Tau 5–4, us="
    val t54: Double?,

    //"Tau 43, us="
    val t43: Double?,

    //"Tau 31 (Yb), us="
    val t31Yb: Double?,

    //"Transfer coefficient, cm^3 s^-1="
    val k: String?,

    //"Back transfer coefficient, a.u.="
    val a: Double?,

    //"Initial 2-level concentration="
    val n2: String?,

    //"Initial 3-level concentration="
    val n3: String?,

    //"Initial 4-level concentration="
    val n4: String?,

    //"Absorption coefficient, cm^-1="
    val ac: Double?,

    //"Excited state absorption cross section for lasing wavelengh, cm^2="
    val sa_wi: Double?,

    //"Excited state absorption cross section for pump wavelengh, cm^2="
    val sa_wip: Double?,

    //"Lasing wavelength, nm="
    val l0: Double?,

    //"Degeneracy factor="
    val m: Double?,

    //"Active element refractive index="
    val ne: Double?,

    //"Stimulated emission cross-section, cm^2="
    val se: String?,

    //"Peak absorption cross-section, cm^2="
    val s0p: String?,

    //q
    var q: Double?,

    //q1
    val q1: Double?,

    //"Excitation quantum yield="
    val hl: Double?,

    //"Branching of luminescence="
    val lb: Double?,

    //"Temperature, K="
    val t: Double?,

    //"1 Atomic percent="
    val ap: String?,

    //"Concentration of sensitizer ion, cm^-3="
    val nsion: String?,

    //"Concentration of working ion, cm^-3="
    //"Concentration Yb, cm^-3="
    val nwion: String?,

    //"Energy N2, cm^-1="
    val e2w: String?,

    //"Energy N3, cm^-1="
    val e3w: String?,

    //"Energy N4, cm^-1="
    val e4w: String?,

    //"Energy N2', cm^-1="
    val e2s: String?,

    //"Lasing scheme="
    val levels: Int?,

    //"Sensitizing="
    val is_sensitizer: Boolean?,

    //Constant in temperature emission coefficient
    val c: Double?,
    ){
//    constructor() : this(null,null,null,null,null,
//        null,null,null,null,null,null,null,null,
//        null,null,null, null, null, null,null,null,
//        null,null,null,null,null,null,null,null,null
//        ,null,null,null,null,null,null,null,null,null,null
//        ,null,null)
    @Ignore var ks: Double? = null
    @Ignore var sp: Double? = null
}