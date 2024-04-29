package org.example.diploma.laser

import org.example.diploma.database.amplifier.AmplifierEntity
import org.example.diploma.database.configuration.ConfigurationEntity
import org.example.diploma.database.laserMedium.LaserMediumEntity
import org.example.diploma.database.optimization.OptimizationEntity
import org.example.diploma.database.pump.PumpEntity
import org.example.diploma.database.qSwitch.QSwitchEntity
import kotlin.math.exp
import kotlin.math.ln
import kotlin.math.sqrt

data class Laser(
    val laserMedium: LaserMediumEntity,
    val configuration: ConfigurationEntity,
    val pump: PumpEntity,
    val qSwitch: QSwitchEntity,
    val optimization: OptimizationEntity,
    val amplifier: AmplifierEntity
) {
    constructor() : this(
        LaserMediumEntity(), ConfigurationEntity(), PumpEntity(),
        QSwitchEntity(), OptimizationEntity(), AmplifierEntity()
    )

    val vc: Double = 29979.2458
    val h: Double = 6.62606896E-28

    //k_b
    val kB = 1.38E-23

    val TM = 1.0

    val hc: Double = 1.9864455003959037E-23

    val dt1 = 1.0
    var se_1_32 = 1.45E-19
    var se_1_44 = 3.8E-20
    var lb_1_32 = 0.01
    var lb_1_44 = 0.01
    var Roc_1_06 = 0.05
    var Roc_1_32 = 0.3
    var Roc_1_44 = 0.97
    var Rhr_1_06 = 0.05
    var Rhr_1_32 = 0.3
    var Rhr_1_44 = 1.0
    var N2_1_44 = 39742620000.0
    var N2_1_32 = 904645200000.0
    var T32_1_32 = 24000.0
    var T32_1_44 = 24000.0
    var FOM_1_32 = 10.0
    var FOM_1_44 = 10.0

    private val ampPulseTicks: Int = 100

    val ts = 10.0

    val length: Int = 100

    val Ffrom = 0.5

    val Fto = 0.999

    val Fn = 12
    val ampDt: Double
        get() = if (this.amplifier.waveform == 0) {
            this.amplifier.ampPulseDuration!! * 3.0 * 0.001 / ampPulseTicks.toDouble()
        } else {
            this.amplifier.ampPulseDuration!! * 2.258 * 0.001 / ampPulseTicks.toDouble()
        }

    val ampDtBig: Double
        get() = this.amplifier.ampLength!! / 29979.2458

    //g_31
    val g31Yb: Double
        get() = 1.0 / this.laserMedium.t31Yb!!

    val g31: Double
        get() = 1.0 / this.laserMedium.t31!!

    val g32: Double
        get() = 1.0 / this.laserMedium.t32!!

    val g21: Double
        get() = 1.0 / this.laserMedium.t21!!

    val g43: Double
        get() = 1.0 / this.laserMedium.t43!!

    val g41: Double
        get() = 1.0 / this.laserMedium.t41!!

    val g51: Double
        get() = 1.0 / this.laserMedium.t51!!

    val g54: Double
        get() = 1.0 / this.laserMedium.t54!!

    val g32_1_44: Double
        get() = 1.0 / this.T32_1_44;

    val g32_1_32: Double
        get() = 1.0 / this.T32_1_32;

    val g21_1_32: Double
        get() = 1.0 / this.laserMedium.t21!!;

    val g21_1_44: Double
        get() = 1.0 / this.laserMedium.t21!!;

    val n2_1_44: Double
        get() = this.N2_1_44 / this.laserMedium.nd?.toDouble()!!;

    val n2_1_32: Double
        get() = this.N2_1_32 / this.laserMedium.nd?.toDouble()!!;

    //Ks
    val ks: Double
        get() = when (this.laserMedium.host) {
            "Er" -> this.laserMedium.ac!! / (this.laserMedium.nyb?.toDouble()!! * this.laserMedium.s0p?.toDouble()!!)
            "Nd" -> this.laserMedium.ac!! / (this.laserMedium.nd?.toDouble()!! * this.laserMedium.s0p?.toDouble()!!)

            "General" -> if (this.laserMedium.is_sensitizer == true) {
                this.laserMedium.ac!! / (this.laserMedium.nsion?.toDouble()!! * this.laserMedium.s0p?.toDouble()!!)
            } else {
                this.laserMedium.ac!! / (this.laserMedium.nwion?.toDouble()!! * this.laserMedium.s0p?.toDouble()!!)
            }

            "Yb" -> this.laserMedium.ac!! / (this.laserMedium.nwion?.toDouble()!! * this.laserMedium.s0p?.toDouble()!!)
            else -> {
                0.0
            }
        }

    //Ag
    val ag: Double
        get() = if (this.configuration.isCylinder == true) {
            this.configuration.hov!! * Math.PI * this.configuration.dia!! * this.configuration.dia / 4.0
        } else {
            this.configuration.hov!! * this.configuration.ld!! * this.configuration.lb!!
        }

    //Ep
    val ep: Double
        get() = this.pump.tp!! * this.pump.wp!! * 1E-06

    //Ap
    val ap: Double
        get() = if (this.pump.scheme == 0) {
            if (this.configuration.isCylinder == true) {
                this.configuration.la!! * (sqrt(Math.PI) * this.configuration.dia!! / 2.0)
            } else {
                this.configuration.la!! * this.configuration.ld!!
            }
        } else {
            if (this.configuration.isCylinder == true) {
                Math.PI * this.configuration.dia!! * this.configuration.dia / 4.0
            } else {
                this.configuration.ld!! * this.configuration.lb!!
            }
        }

    //Lp
    val lp: Double
        get() = if (this.pump.scheme != 0) {
            this.configuration.la!!
        } else {
            if (this.configuration.isCylinder == true) {
                sqrt(Math.PI) * this.configuration.dia!! / 2.0
            } else {
                this.configuration.lb!!
            }
        }

    //Fav
    val fav: Double
        get() = -(this.pump.rp!! * exp(-this.laserMedium.ac!! * this.lp) + 1.0
                ) * (exp(-this.laserMedium.ac * this.lp) - 1.0) / (this.laserMedium.ac * this.lp)

    //P0
    val p0: Double
        get() = this.pump.wp!! * this.pump.hc!! / this.ap

    //P
    val p: Double
        get() = this.fav / this.p0

    val sp: Double
        get() = this.laserMedium.s0p?.toDouble()!! * this.ks

    //Issp
    val issp: Double
        get() = when (this.laserMedium.host) {
            "Er" -> this.hc / this.laserMedium.ne!! / this.pump.lp!! / 1E-07 /
                    (2.0 * this.sp * this.laserMedium.t31Yb!!) * 1000000.0

            "Nd" -> this.hc / this.laserMedium.ne!! / this.pump.lp!! / 1E-07 /
                    (this.laserMedium.s0p!!.toDouble() * this.laserMedium.t43!!) * 1000000.0

            "General" -> {
                if (this.laserMedium.is_sensitizer == true) {
                    this.hc / this.laserMedium.ne!! / this.pump.lp!! / 1E-07 /
                            (2.0 * this.sp * this.laserMedium.t31Yb!!) * 1000000.0
                } else {
                    if (this.laserMedium.levels == 0) {
                        this.hc / this.laserMedium.ne!! / this.pump.lp!! / 1E-07 /
                                (this.sp * this.laserMedium.t43!!) * 1000000.0
                    } else {
                        this.hc / this.laserMedium.ne!! / this.pump.lp!! / 1E-07 /
                                (this.sp * this.laserMedium.t32!!) * 1000000.0
                    }
                }
            }

            "Yb" -> this.hc / this.laserMedium.ne!! / this.pump.lp!! / 1E-07 /
                    (this.sp * this.laserMedium.t43!!) * 1000000.0

            else -> {
                0.0
            }
        }

    val fform: Double
        get() = if (this.pump.pformId == 0) {
            1.0
        } else {
            this.pump.tp!! / (this.pump.tp - (this.pump.t1p!! + this.pump.t2p!!) / 2.0)
        }

    //Epph
    val epph: Double
        get() = this.hc / (this.pump.lp!! * 1E-07)

    //E0ph
    val e0ph: Double
        get() = this.hc / (this.laserMedium.l0!! * 1E-07);

    //Ip
    val ip: Double
        get() = this.p / this.epph * 1E-06

    val k: Double
        get() = when (this.laserMedium.host) {
            "Er" -> this.laserMedium.k?.toDouble()!! * this.laserMedium.ner?.toDouble()!! * 1E-06
            "Nd" -> 0.0
            "General" -> this.laserMedium.k?.toDouble()!! * this.laserMedium.nwion?.toDouble()!! * 1E-06
            "Yb" -> 0.0
            else -> 0.0
        }

    val ksi: Double
        get() = when (this.laserMedium.host) {
            "Er" -> this.laserMedium.nyb?.toDouble()!! / this.laserMedium.ner?.toDouble()!!
            "Nd" -> 0.0
            "General" -> this.laserMedium.nsion?.toDouble()!! / this.laserMedium.nwion?.toDouble()!!
            "Yb" -> 0.0
            else -> 0.0
        }

    //Isp
    val isp: Double
        get() = when (this.laserMedium.host) {
            "Er" -> 1.0 / (2.0 * this.sp * this.laserMedium.t31Yb!!)
            "Nd" -> 1.0 / (this.sp * this.laserMedium.t43!!)
            "General" -> if (this.laserMedium.levels == 1) {
                if (this.laserMedium.is_sensitizer == true) {
                    1.0 / (2.0 * this.sp * this.laserMedium.t31Yb!!)
                } else {
                    1.0 / (this.sp * this.laserMedium.t43!!)
                }
            } else {
                if (this.laserMedium.is_sensitizer == true) {
                    1.0 / (2.0 * this.sp * this.laserMedium.t31Yb!!)
                } else {
                    1.0 / (this.sp * this.laserMedium.t32!!)
                }
            }

            "Yb" -> 1.0 / (this.sp * this.laserMedium.t43!!)
            else -> 0.0
        }

    val iss: Double
        get() = when (this.laserMedium.host) {
            "Er" -> 1.0 / (2.0 * this.laserMedium.se?.toDouble()!! * this.laserMedium.t21!!)
            "Nd" -> 1.0 / (this.laserMedium.se?.toDouble()!! * this.laserMedium.t32!!)
            "General" -> if (this.laserMedium.levels == 1) {
                if (this.laserMedium.is_sensitizer == true) {
                    1.0 / (2.0 * this.laserMedium.se?.toDouble()!! * this.laserMedium.t32!!)
                } else {
                    1.0 / (this.laserMedium.se?.toDouble()!! * this.laserMedium.t32!!)
                }
            } else {
                if (this.laserMedium.is_sensitizer == true) {
                    1.0 / (2.0 * this.laserMedium.se?.toDouble()!! * this.laserMedium.t21!!)
                } else {
                    1.0 / (this.laserMedium.se?.toDouble()!! * this.laserMedium.t31!!)
                }
            }

            "Yb" -> 1.0 / (this.laserMedium.se?.toDouble()!! * this.laserMedium.t32!!)
            else -> 0.0
        }

    val iS: Double
        get() = this.ip * 1E-06
    val s0: Double
        get() = this.iS / this.iss;

    //Fp
    val fp: Double
        get() = this.ip / this.isp

    val shutter: Int
        get() = when {
            this.qSwitch.isAQS!! && this.qSwitch.isPQS!! && this.qSwitch.isQSwitch!! -> 3
            this.qSwitch.isPQS!! && this.qSwitch.isQSwitch!! -> 2
            this.qSwitch.isAQS && this.qSwitch.isQSwitch!! -> 1
            else -> 0 //no qswitch
        }
    val lo: Double
        get() = when (this.shutter) {
            0 -> this.configuration.lc!! + (this.laserMedium.ne!! - 1.0) * this.configuration.la!!
            1 -> this.configuration.lc!! + (this.laserMedium.ne!! - 1.0
                    ) * this.configuration.la!! + (this.qSwitch.nsh!! - 1.0) * this.qSwitch.lsh!!

            2 -> this.configuration.lc!! + (this.laserMedium.ne!! - 1.0
                    ) * this.configuration.la!! + (this.qSwitch.npsh!! - 1.0) * this.qSwitch.lpsh!!

            3 -> this.configuration.lc!! + (this.laserMedium.ne!! - 1.0
                    ) * this.configuration.la!! + (this.qSwitch.npsh!! - 1.0) * this.qSwitch.lpsh!! + (
                    this.qSwitch.nsh!! - 1.0) * this.qSwitch.lsh!!

            else -> 0.0
        }

    val lac: Double
        get() = this.configuration.la!! * this.lo


    val b: Double
        get() = when (this.laserMedium.host) {
            "Er" -> this.laserMedium.se?.toDouble()!! * this.laserMedium.ner?.toDouble()!! * 29979.2458
            "Nd" -> this.laserMedium.se?.toDouble()!! * this.laserMedium.nd?.toDouble()!! * 29979.2458
            "General" -> this.laserMedium.se?.toDouble()!! * this.laserMedium.nwion?.toDouble()!! * 29979.2458
            "Yb" -> this.laserMedium.se?.toDouble()!! * this.laserMedium.nwion?.toDouble()!! * 29979.2458
            else -> 0.0
        }

    val tc: Double
        get() = 2.0 * this.lo / 29979.2458 / (-ln(
            this.configuration.roc!! * (
                    1.0 - this.configuration.gc!!)
        ) + 2.0 * this.configuration.ga!! * this.configuration.la!!)

    val n2: Double
        get() = this.laserMedium.n2?.toDouble()!! / this.laserMedium.nd?.toDouble()!!

    val n3: Double
        get() = this.laserMedium.n3?.toDouble()!! / this.laserMedium.nd?.toDouble()!!

    val n4: Double
        get() = this.laserMedium.n4?.toDouble()!! / this.laserMedium.nd?.toDouble()!!

    val pformt: Array<DoubleArray>
        get() {
            val hehe = Array(2) { DoubleArray(length) }
            for (index1 in 0 until 2) {
                for (index2 in 0 until length) {
                    hehe[index1][index2] = 0.0
                }
            }
            hehe[0][0] = 0.0
            hehe[1][0] = 0.0
            hehe[0][1] = 0.0
            hehe[1][1] = 300.0
            hehe[1][2] = 300.0
            hehe[0][2] = 1500.0
            hehe[0][3] = 1500.0
            hehe[1][3] = 0.0
            hehe[1][4] = 0.0
            hehe[0][4] = 2000.0
            hehe[0][5] = 2000.0
            hehe[1][5] = 300.0
            hehe[1][6] = 300.0
            hehe[0][6] = 2500.0
            hehe[0][7] = 2500.0
            hehe[1][7] = 100.0
            hehe[1][8] = 100.0
            hehe[0][8] = 3000.0
            hehe[0][9] = 0.0
            hehe[1][9] = 0.0

            return hehe
        }

    val tpp: Double
        get() {
            var tp = this.pump.tp!!
            if (this.pump.pformId == 0 || this.pump.pformId == 1) {
                when (this.shutter) {
                    0 -> return if (this.laserMedium.host == "Er" || (
                                this.laserMedium.host == "General" && this.laserMedium.is_sensitizer == true)
                    ) {
                        tp + this.ts + this.laserMedium.t31Yb!!
                    } else {
                        tp + this.ts
                    }

                    1 -> return maxOf(
                        this.qSwitch.sts!! + this.ts + this.qSwitch.stoff!!,
                        tp + this.ts
                    )

                    2 -> return maxOf(tp + this.qSwitch.spt!!, tp + this.ts)
                    3 -> return maxOf(
                        maxOf(this.qSwitch.sts!! + this.ts + this.qSwitch.stoff!!, tp + this.ts),
                        maxOf(tp + this.qSwitch.spt!!, tp + this.ts)
                    )

                    else -> {
                        // Error handling
                        val num1 = 0
                    }
                }
            }
            if (this.pump.pformId == 2) {
                for (index in 2 until this.pformt[0].size) {
                    if (this.pformt[0][index] == 0.0) {
                        tp = this.pformt[0][index - 1]
                        break
                    }
                }
                when (this.shutter) {
                    0 -> return tp + this.ts
                    1 -> return maxOf(
                        this.qSwitch.sts!! + this.ts + this.qSwitch.stoff!!,
                        tp + this.ts
                    )

                    2 -> return maxOf(tp + this.qSwitch.spt!!, tp + this.ts)
                    3 -> return maxOf(
                        maxOf(this.qSwitch.sts!! + this.ts + this.qSwitch.stoff!!, tp + this.ts),
                        maxOf(tp + this.qSwitch.spt!!, tp + this.ts)
                    )

                    else -> {
                        // Error handling
                        val num2 = 0
                    }
                }
            }
            return tp
        }

    val tpp_sim: Double
        get() {
            var tp = this.pump.tp!!
            if (this.pump.pformId == 0 || this.pump.pformId == 1 || this.pump.pformId != 2) {
                return tp
            }
            for (index in 2 until this.pformt[0].size) {
                if (this.pformt[0][index] == 0.0) {
                    tp = this.pformt[0][index - 1]
                    break
                }
            }
            return tp
        }

    val nth: Double
        get() = when (laserMedium.host) {
            "Nd" -> {
                (-ln(configuration.roc!! * (1.0 - configuration.gc!!)) + 2.0 * configuration.ga!! * configuration.la!!
                        ) / (2.0 * laserMedium.se?.toDouble()!! * configuration.la * laserMedium.nd?.toDouble()!!)
            }

            "Er:Host" -> (-ln(
                configuration.roc!! * (1.0 - configuration.gc!!)
            ) + 2.0 * configuration.ga!! * configuration.la!!) / (2.0 * laserMedium.se?.toDouble()!! * configuration.la * laserMedium.ner?.toDouble()!!)

            "General", "Yb:Host" -> (-ln(
                configuration.roc!! * (1.0 - configuration.gc!!)
            ) + 2.0 * configuration.ga!! * configuration.la!!) / (2.0 * laserMedium.se?.toDouble()!! * configuration.la * laserMedium.nwion?.toDouble()!!)

            else -> {
                0.0
            }
        }

}