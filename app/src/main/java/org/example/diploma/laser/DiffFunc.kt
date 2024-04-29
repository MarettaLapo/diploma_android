package org.example.diploma.laser

import kotlin.math.abs
import kotlin.math.exp
import kotlin.math.ln
import kotlin.math.min
import kotlin.math.pow
import kotlin.math.sin

class DiffFunc(private val laser: Laser) {


    var g_31: Double = laser.g31Yb
    var sa_wi: Double = laser.laserMedium.sa_wi ?: 0.0
    var sa_wip: Double = laser.laserMedium.sa_wip ?: 0.0
    var Fp: Double = laser.fp
    var k: Double = laser.k
    var A0: Double = laser.laserMedium.a ?: 0.0
    var ksi0: Double = laser.ksi
    var g31: Double = laser.g31
    var g32: Double = laser.g32
    var g21: Double = laser.g21
    var g41: Double = laser.g41
    var g51: Double = laser.g51
    var g54: Double = laser.g54

    var S0: Double = laser.s0
    var m0: Double = laser.laserMedium.m!!

    var Lac: Double = laser.lac
    var b: Double = laser.b

    var tc: Double = laser.tc

    var sts: Double = laser.qSwitch.sts!!
    var sf: Double = laser.qSwitch.sf!!
    var sk: Double = laser.qSwitch.sk!!
    var sb: Double = laser.qSwitch.sb!!
    var sTf: Double = laser.qSwitch.stf!!

    var sMode: Int = laser.qSwitch.mode!!//string

    var sT0: Double = laser.qSwitch.st0!!
    var sTmax: Double = laser.qSwitch.stmax!!
    var sToff: Double = laser.qSwitch.stoff!!

    var sFrontType: Int = laser.qSwitch.sFrontType!! //string

    var dt1: Double = laser.dt1

    var g43: Double = laser.g43

    var HL: Double = laser.laserMedium.hl ?: 0.0
    var lb: Double = laser.laserMedium.lb ?: 0.0
    var q1: Double = laser.laserMedium.q1 ?: 0.0
    var q: Double = laser.laserMedium.q ?: 0.0
    var n2: Double = laser.n2
    var n3: Double = laser.n3
    var n4: Double = laser.n4

    var ssh: Double = laser.qSwitch.ssh?.toDouble()!!
    var spt: Double = laser.qSwitch.spt!!
    var spt42: Double = 0.1
    var SIG: Double = laser.qSwitch.fom!!
    var Lsh: Double = laser.qSwitch.lsh!!
    var Lpsh: Double = laser.qSwitch.lpsh!!
    var spTD: Double = laser.qSwitch.sptd!!
    var spT0: Double = laser.qSwitch.spt0!!

    var Nsh: Double = -ln(spT0) / (laser.qSwitch.lpsh!! * laser.qSwitch.ssh?.toDouble()!!)

    var ac_shutter: Double = laser.qSwitch.absCoef!!

    var Lo: Double = laser.lo
    var vc: Double = 29979.2458
    var Roc: Double = laser.configuration.roc!!
    var gc: Double = laser.configuration.gc!!
    var ga: Double = laser.configuration.ga!!
    var La: Double = laser.configuration.la!!

    var se_1_06: Double = laser.laserMedium.se?.toDouble()!!
    var se_1_32: Double = laser.se_1_32
    var se_1_44: Double = laser.se_1_44

    //не надо
    var AveragePhotonAmpLength: Double = laser.laserMedium.AveragePhotonAmpLength

    var Nd: Double = laser.laserMedium.nd?.toDouble()!!
    var amp_1_06: Double = Nd * AveragePhotonAmpLength * se_1_06
    var amp_1_32: Double = Nd * AveragePhotonAmpLength * se_1_32
    var amp_1_44: Double = Nd * AveragePhotonAmpLength * se_1_44
    var g32_1_32: Double = laser.g32_1_32
    var g32_1_44: Double = laser.g32_1_44
    var g21_1_32: Double = laser.g21_1_32
    var g21_1_44: Double = laser.g21_1_44
    var lb_1_32: Double = laser.lb_1_32
    var lb_1_44: Double = laser.lb_1_44
    var n2_1_32: Double = laser.n2_1_32
    var n2_1_44: Double = laser.n2_1_44
    var FOM_1_32: Double = laser.FOM_1_32
    var FOM_1_44: Double = laser.FOM_1_44

    var cyl: Boolean = laser.configuration.isCylinder!!

    var Wp: Double = laser.pump.wp!!
    var Hc: Double = laser.pump.hc!!

    var Ld: Double = laser.configuration.ld!!
    var Lb: Double = laser.configuration.lb!!
    var DIA: Double = laser.configuration.dia!!

    var P0: Double = laser.p0
    var Rp: Double = laser.pump.rp!!
    var a: Double = laser.laserMedium.ac!!
    var Lp: Double = laser.lp
    var Epph: Double = laser.epph
    var Nwion: Double = laser.laserMedium.nwion?.toDouble() ?: 0.0
    var Nsion: Double = laser.laserMedium.nsion?.toDouble() ?: 0.0
    var Ner: Double = laser.laserMedium.ner?.toDouble() ?: 0.0
    var Nyb: Double = laser.laserMedium.nyb?.toDouble() ?: 0.0

    fun setColumn(M: Array<DoubleArray>, m: DoubleArray, i: Int):
            Array<DoubleArray> {
        val length = m.size
        var tmp: Array<DoubleArray> = M.copyOf()
        if (i == M[0].size) {
            val numArray = Array(length) { DoubleArray(i + 500000) }
            for (index1 in 0 until length) {
                for (index2 in 0 until i) {
                    numArray[index1][index2] = M[index1][index2]
                }
            }
            for (index in 0 until length) {
                numArray[index][i] = m[index]
            }
            tmp = numArray
        }
        for (index in 0 until length) {
            tmp[index][i] = m[index]
        }
        return tmp
    }

    private fun ColumnTo1DArray(
        M: Array<DoubleArray>,
        newSS: DoubleArray,
        j: Int,
        iStart: Int,
        iEnd: Int
    ) {
        for (index in iStart..iEnd) {
            newSS[index - iStart] = M[index][j]
        }
    }

    fun Fp_t(x: Double): Double {
        var num1 = 0.0
        if (x <= 0.0 || x > laser.tpp_sim)
            return 0.0
        if (laser.pump.pformId == 0)
            return Fp
        if (laser.pump.pformId == 1) {
            val t1p = laser.pump.t1p!!
            val t2p = laser.pump.t2p!!
            if (x < t1p)
                return Fp / t1p * x
            if (x >= t1p && x <= laser.pump.tp!! - t2p)
                return Fp
            if (x > laser.pump.tp!! - t2p)
                return Fp / -t2p * (x - laser.pump.tp)
        }
        if (laser.pump.pformId == 2) {
            val length = laser.pformt[0].size
            for (index in 1 until length) {
                if (x >= laser.pformt[0][index - 1] && x <= laser.pformt[0][index]) {
                    val num2 = laser.p / laser.pump.wp!!
                    if (x == laser.pformt[0][index - 1])
                        return laser.pformt[1][index]
                    val num3 = (laser.pformt[1][index] - laser.pformt[1][index - 1]) / (
                            laser.pformt[0][index] - laser.pformt[0][index - 1])
                    num1 =
                        num2 * (num3 * (x - laser.pformt[0][index - 1]) + laser.pformt[1][index - 1])
                    break
                }
            }
        }
        return Fp / laser.p * num1
    }


    fun Fp_t(x: Double, alpha: Double): Double {
        val Fp = if (!cyl) {
            Wp * Hc * (-(Rp * exp(alpha) + 1.0) * (exp(alpha) - 1.0)) / (La * Lb * Ld * Epph) * 1E-06
        } else {
            Wp * Hc * (-(Rp * exp(alpha) + 1.0) * (exp(alpha) - 1.0)) / (La * (Math.PI * DIA * DIA / 4.0) * Epph) * 1E-06
        }

        var num1 = 0.0

        if (x <= 0.0 || x > laser.tpp_sim) return 0.0

        when (laser.pump.pformId) {
            0 -> return Fp
            1 -> {
                val t1p = laser.pump.t1p!!
                val t2p = laser.pump.t2p!!
                return when {
                    x < t1p -> Fp / t1p * x
                    x >= t1p && x <= laser.pump.tp!! - t2p -> Fp
                    else -> Fp / -t2p * (x - laser.pump.tp!!)
                }
            }

            2 -> {
                val length = laser.pformt[0].size
                for (index in 1 until length) {
                    if (x >= laser.pformt[0][index - 1] && x <= laser.pformt[0][index]) {
                        val num2 = laser.p / laser.pump.wp!!
                        if (x == laser.pformt[0][index - 1]) return laser.pformt[1][index]
                        val num3 =
                            (laser.pformt[1][index] - laser.pformt[1][index - 1]) / (laser.pformt[0][index] - laser.pformt[0][index - 1])
                        num1 =
                            num2 * (num3 * (x - laser.pformt[0][index - 1]) + laser.pformt[1][index - 1])
                        break
                    }
                }
            }
        }

        return Fp / laser.p * num1
    }

    private fun Tfsh(t: Double): Double {
        val num = if (t >= sts) 1.0 else 0.0
        val y = if (sFrontType == 0) 2.0 else 1.0
        return sTmax - (sTmax - sT0) * exp(-abs(num * (t - sts) / sTf).pow(y))
    }

    private fun FtirTbsh(t: Double): Double {
        val num1 = 4.0 * sTf
        val num2 = 0.2
        val num3 = if (t >= sts) 1.0 else 0.0
        val y = if (sFrontType == 0 || sFrontType == 1) 1.0 else 2.0
        return num2 + (sTmax - num2) * exp(-abs(num3 * (t - sts - num1 - sToff) / sTf).pow(y))
    }

    private fun Tsinc(t: Double): Double {
        val num1 = 2.0 * sTf
        val num2 = 4.0 * sTf
        val num3 = 4.0 * sTf
        val num4 = 4.0 * num1
        val num5 = 0.2
        val num6 = 4.4931
        val num7 = -0.34757440000000006
        val num8 = 3.0
        val num9 = t - sts - num3 - sToff - num4
        val num10 = if (t >= sts) 1.0 else 0.0
        val y = if (sFrontType == 0 || sFrontType == 1) 1.0 else 2.0
        val num11 = sT0 + (1.0 - sT0) * exp(-abs(num10 * num9 / num2).pow(y))
        return sTmax / (num11 + num5 - num7) * (sin(num8 * num9 - num6) / (num8 * num9 - num6) * num11 + num5 - num7)
    }

    private fun FtirTs(t: Double): Double {
        val num2 = 2.0 * sTf
        val num3 = 2.0 * sTf
        val num4 = 4.0 * sTf
        val num5 = 4.0 * num2
        val num6 = 4.0 * num3
        var num1 = 0.0
        when {
            t <= sts || t > sts + num4 + sToff + num5 + num6 -> num1 = sT0
            t > sts && t <= sts + num4 -> num1 = Tfsh(t)
            t > sts + num4 && t <= sts + num4 + sToff -> num1 = sTmax
            t > sts + num4 + sToff && t <= sts + num4 + sToff + num5 -> num1 = FtirTbsh(t)
            t > sts + num4 + sToff + num5 && t <= sts + num4 + sToff + num5 + num6 -> num1 =
                Tsinc(t)
        }
        return num1 * exp(-ac_shutter * Lsh)
    }

    private fun Tbsh(t: Double): Double {
        val num1 = 4.0 * sTf
        val num2 = if (t >= sts) 1.0 else 0.0
        val y = if (sFrontType == 0) 1.0 else 2.0
        val num3 = sk * (t - sts) + sb
        val num4 = if (sMode == 2) sToff else sk * (t - sts) + sb
        return sT0 + (sTmax - sT0) * exp(-abs(num2 * (t - sts - num1 - num4) / sTf).pow(y))
    }

    fun Ts(t: Double): Double {
        var num1 = 0.0
        val num2 = 4.0 * sTf
        val sts = sts
        when (sMode) {
            0 -> {
                if (laser.qSwitch.AQStype == 2 || laser.qSwitch.AQStype == 3) {
                    num1 = FtirTs(t)
                } else {
                    when {
                        t <= sts -> num1 = sT0
                        t > sts && t <= sts + num2 -> num1 = Tfsh(t)
                        t > sts + num2 && t <= sts + num2 + sToff -> num1 = sTmax
                        t > sts + num2 + sToff && t <= sts + 2.0 * num2 + sToff -> num1 = Tbsh(t)
                        t > sts + 2.0 * num2 + sToff -> num1 = sT0
                    }
                }
            }

            1 -> {
                val y1 = if (sFrontType == 0) 1.0 else 2.0
                for (index in 0 until 100) {
                    val num3 = sts + index * 1000 / sf
                    val num4 = if (t >= num3) 1.0 else 0.0
                    when {
                        t <= sts -> num1 = sT0
                        t > num3 && t <= num3 + num2 -> {
                            num1 =
                                sTmax - (sTmax - sT0) * exp(-abs(num4 * (t - num3) / sTf).pow(y1))
                        }

                        t > num3 + num2 && t <= num3 + num2 + sToff -> num1 = sTmax
                        t > num3 + num2 + sToff && t <= num3 + 2.0 * num2 + sToff -> {
                            num1 = sT0 + (sTmax - sT0) * exp(
                                -abs(num4 * (t - num3 - num2 - sToff) / sTf).pow(y1)
                            )
                        }

                        t > num3 + 2.0 * num2 + sToff && t < sts + (index + 1) * 1000 / sf -> num1 =
                            sT0
                    }
                }
            }

            2 -> {
                val y2 = if (sFrontType == 0) 1.0 else 2.0
                val num5 = sk * (t - sts) + sb
                for (index in 0 until 100) {
                    val num6 = sts + index * 1000 / sf
                    val num7 = if (t >= num6) 1.0 else 0.0
                    when {
                        t <= sts -> num1 = sT0
                        t > num6 && t <= num6 + num2 -> {
                            num1 =
                                sTmax - (sTmax - sT0) * exp(-abs(num7 * (t - num6) / sTf).pow(y2))
                        }

                        t > num6 + num2 && t <= num6 + num2 + num5 -> num1 = sTmax
                        t > num6 + num2 + num5 && t <= num6 + 2.0 * num2 + num5 -> {
                            num1 = sT0 + (sTmax - sT0) * exp(
                                -abs(num7 * (t - num6 - num2 - num5) / sTf).pow(y2)
                            )
                        }

                        t > num6 + 2.0 * num2 + num5 && t < sts + (index + 1) * 1000 / sf -> num1 =
                            sT0
                    }
                }
            }

            else -> {
                // Handle the error case
                val num8 = error("Wrong Q-switch mode.")
            }
        }
        return num1 * exp(-ac_shutter * Lsh)
    }

    fun ga_t(t: Double): Double {
        return ga
    }

    fun tc_t(t: Double): Double {
        return when (laser.shutter) {
            0 -> {
                2.0 * laser.lo / vc / (-ln(laser.configuration.roc!! * (1.0 - laser.configuration.gc!!)) + 2.0 * ga_t(
                    t
                ) * laser.configuration.la!!)
            }

            1 -> {
                when (laser.qSwitch.AQStype) {
                    0 -> {
                        2.0 * laser.lo / vc / (-ln(laser.configuration.roc!! * Ts(t).pow(2.0) * (1.0 - laser.configuration.gc!!)) + 2.0 * ga_t(
                            t
                        ) * laser.configuration.la!!)
                    }

                    1 -> {
                        2.0 * laser.lo / vc / (-ln(laser.configuration.roc!! * Ts(t) * (1.0 - laser.configuration.gc!!)) + 2.0 * ga_t(
                            t
                        ) * laser.configuration.la!!)
                    }

                    2 -> {
                        val num1 = Ts(t)
                        2.0 * laser.lo / vc / (-ln((1.0 - num1) / (1.0 + num1) * (1.0 - laser.configuration.gc!!)) + 2.0 * ga_t(
                            t
                        ) * laser.configuration.la!!)
                    }

                    3 -> {
                        val num2 = Ts(t)
                        2.0 * laser.lo / vc / (-ln(num2 / (2.0 - num2) * (1.0 - laser.configuration.gc!!)) + 2.0 * ga_t(
                            t
                        ) * laser.configuration.la!!)
                    }

                    else -> {
                        // Handle the error case
                        0.0
                    }
                }
            }

            else -> {
                // Handle the error case
                0.0
            }
        }
    }

    fun tc_t(t: Double, T: Double): Double {
        when (laser.shutter) {
            2 -> {
                val expTerm =
                    -ln(laser.configuration.roc!! * T.pow(2.0) * (1.0 - laser.configuration.gc!!)) + 2.0 * ga_t(
                        t
                    ) * laser.configuration.la!!
                return 2.0 * laser.lo / vc / expTerm
            }

            3 -> {
                return when (laser.qSwitch.AQStype) {
                    0 -> {
                        val expTerm =
                            -ln(laser.configuration.roc!! * T.pow(2.0) * Ts(t).pow(2.0) * (1.0 - laser.configuration.gc!!)) + 2.0 * ga_t(
                                t
                            ) * laser.configuration.la!!
                        2.0 * laser.lo / vc / expTerm
                    }

                    1 -> {
                        val expTerm =
                            -ln(laser.configuration.roc!! * T.pow(2.0) * Ts(t) * (1.0 - laser.configuration.gc!!)) + 2.0 * ga_t(
                                t
                            ) * laser.configuration.la!!
                        2.0 * laser.lo / vc / expTerm
                    }

                    2 -> {
                        val num1 = Ts(t)
                        val expTerm =
                            -ln(T.pow(2.0) * (1.0 - num1) / (1.0 + num1) * (1.0 - laser.configuration.gc!!)) + 2.0 * ga_t(
                                t
                            ) * laser.configuration.la!!
                        2.0 * laser.lo / vc / expTerm
                    }

                    3 -> {
                        val num2 = Ts(t)
                        val expTerm =
                            -ln(T.pow(2.0) * num2 / (2.0 - num2) * (1.0 - laser.configuration.gc!!)) + 2.0 * ga_t(
                                t
                            ) * laser.configuration.la!!
                        2.0 * laser.lo / vc / expTerm
                    }

                    else -> {
                        0.0
                    }
                }
            }

            else -> {
                return 0.0
            }
        }
    }

    fun ThresholdCheck(newSS: DoubleArray, t: Double, Tps: Double): Boolean {
        var num1 = 0.0
        var num2 = 0.0
        var num3 = 0.0
        var num4: Double
        when (laser.laserMedium.host) {
            "Nd" -> {
                num4 = Nd
                num2 = laser.configuration.roc!!
                num3 = laser.laserMedium.se?.toDouble()!!
                num1 = newSS[1] - newSS[2]
            }

            "Er:Host" -> {
                num4 = Ner
                num2 = laser.configuration.roc!!
                num3 = laser.laserMedium.se?.toDouble()!!
                num1 = newSS[2] - m0 * (1.0 - newSS[1] - newSS[2])
            }

            "General" -> {
                num4 = Nwion
                num2 = laser.configuration.roc!!
                num3 = laser.laserMedium.se?.toDouble()!!
                num1 = if (laser.laserMedium.levels == 1) {
                    if (laser.laserMedium.is_sensitizer == false) {
                        newSS[1] - m0 * (1.0 - newSS[0] - newSS[1])
                    } else {
                        newSS[2] - m0 * (1.0 - newSS[1] - newSS[2])
                    }
                } else {
                    if (laser.laserMedium.is_sensitizer == false) {
                        newSS[1] - m0 * newSS[2]
                    } else {
                        newSS[2] - m0 * newSS[3]
                    }
                }
            }

            "Yb:Host" -> {
                num4 = Nwion
                num2 = laser.configuration.roc!!
                num3 = laser.laserMedium.se?.toDouble()!!
                num1 = newSS[1] - m0 * newSS[2]
            }

            else -> {
                return false
            }
        }
        return when (laser.shutter) {
            0 -> num1 >= laser.nth
            1 -> {
                val num7 = Ts(t)
                when (laser.qSwitch.AQStype) {
                    0 -> num1 >= (-ln(num2 * (1.0 - gc) * num7 * num7) + 2.0 * ga * La) / (2.0 * num3 * La * num4)
                    1 -> num1 >= (-ln(num2 * (1.0 - gc) * num7) + 2.0 * ga * La) / (2.0 * num3 * La * num4)
                    2 -> num1 >= (-ln((1.0 - num7) / (1.0 + num7) * (1.0 - gc)) + 2.0 * ga * La) / (2.0 * num3 * La * num4)
                    3 -> num1 >= (-ln(num7 / (2.0 - num7) * (1.0 - gc)) + 2.0 * ga * La) / (2.0 * num3 * La * num4)
                    else -> false
                }
            }

            2 -> num1 >= (-ln(num2 * (1.0 - gc) * Tps * Tps) + 2.0 * ga * La) / (2.0 * laser.laserMedium.se.toDouble() * La * num4)
            3 -> {
                val num8 = Ts(t)
                when (laser.qSwitch.AQStype) {
                    0 -> num1 >= (-ln(num2 * (1.0 - gc) * num8 * num8 * Tps * Tps) + 2.0 * ga * La) / (2.0 * num3 * La * num4)
                    1 -> num1 >= (-ln(num2 * (1.0 - gc) * num8 * Tps * Tps) + 2.0 * ga * La) / (2.0 * num3 * La * num4)
                    2 -> num1 >= (-ln((1.0 - num8) / (1.0 + num8) * (1.0 - gc) * Tps * Tps) + 2.0 * ga * La) / (2.0 * num3 * La * num4)
                    3 -> num1 >= (-ln(num8 / (2.0 - num8) * (1.0 - gc) * Tps * Tps) + 2.0 * ga * La) / (2.0 * num3 * La * num4)
                    else -> false
                }
            }

            else -> {
                false
            }
        }
    }

    fun D(Y: DoubleArray, m: DoubleArray, t: Double) {
        val length = Y.size
        for (index in 0 until length) {
            if (Y[index] < 0.0)
                Y[index] = 0.0
        }
        when (laser.laserMedium.host) {
            "Er" -> {
                m[0] =
                    0.5 * this.g_31 * (1.0 - 2.0 * Y[0]) * this.Fp_t(t) - this.k * Y[0] * (1.0 - Y[2] - Y[1]) - this.g_31 * Y[0] + this.A0 * this.k * Y[1] * (1.0 - Y[0])
                m[1] =
                    this.ksi0 * this.k * Y[0] * (1.0 - Y[2] - Y[1]) - Y[1] * (this.g31 + this.g32) - this.ksi0 * this.A0 * this.k * Y[1] * (1.0 - Y[0]) + 0.5 * this.Fp_t(
                        t
                    ) * this.g_31 * (1.0 - Y[2] - 2.0 * Y[1])
                m[2] =
                    this.g32 * Y[1] - this.g21 * Y[2] - 0.5 * this.g21 * (Y[2] - this.m0 * (1.0 - Y[2] - Y[1])) * Y[3]
                if (laser.shutter == 2 || laser.shutter == 3) {
                    m[6] =
                        exp(-(this.ssh * (this.Nsh - 2.0 * Y[4] - Y[5] + (Y[4] - Y[5]) / this.SIG)) * this.Lpsh)
                    m[3] =
                        this.Lac * this.b * (Y[2] - this.m0 * (1.0 - Y[2] - Y[1])) * Y[3] - (Y[3] - this.S0) / this.tc_t(
                            t,
                            m[6]
                        )
                    m[5] =
                        this.ssh / this.SIG * (Y[4] - Y[5]) / (this.ssh * (this.Nsh - 2.0 * Y[4] - Y[5]) + this.ssh / this.SIG * (Y[4] - Y[5])) / this.Lpsh * laser.TM * laser.TM * Y[3] * laser.iss * (1.0 - m[6]) - Y[5] / this.spt42
                    m[4] =
                        this.ssh * (this.Nsh - 2.0 * Y[4] - Y[5]) / (this.ssh * (this.Nsh - 2.0 * Y[4] - Y[5]) + this.ssh / this.SIG * (Y[4] - Y[5])) / this.Lpsh * this.laser.TM * this.laser.TM * Y[3] * laser.iss * (1.0 - m[6]) - Y[4] / this.spt - m[5]
                } else {
                    m[3] =
                        this.Lac * this.b * (Y[2] - this.m0 * (1.0 - Y[2] - Y[1])) * Y[3] - Y[3] / this.tc_t(
                            t
                        ) + this.S0 / this.tc_t(t)
                }
            }

            "Nd" -> {
                val a = a
                val num1 = Y[0]
                val num2 = Y[1]
                val num3 = Y[2]
                m[0] =
                    g43 * (1.0 - 2.0 * Y[0] - Y[1] - Y[2]) * Fp_t(t) - g43 / HL * Y[0]
                m[1] =
                    g43 * Y[0] - (g32 + g31) * Y[1] - g32 * (Y[1] - m0 * Y[2]) * Y[3]
                m[2] =
                    lb * g32 * Y[1] - g21 * Y[2] + g32 * (Y[1] - m0 * Y[2]) * Y[3] + q * g21 * (1.0 - Y[0] - Y[1])
                if (laser.shutter == 2 || laser.shutter == 3) {
                    m[6] =
                        exp(-(ssh * (Nsh - 2.0 * Y[4] - Y[5] + (Y[4] - Y[5]) / SIG)) * Lpsh)
                    m[3] =
                        Lac * b * (Y[1] - m0 * Y[2]) * Y[3] + (S0 * lb - Y[3]) / tc_t(
                            t,
                            m[6]
                        )
                    m[5] =
                        ssh / SIG * (Y[4] - Y[5]) / (ssh * (Nsh - 2.0 * Y[4] - Y[5]) + ssh / SIG * (Y[4] - Y[5])) / Lpsh * laser.TM * laser.TM * Y[3] * laser.iss * (1.0 - m[6]) - Y[5] / spt42
                    m[4] =
                        ssh * (Nsh - 2.0 * Y[4] - Y[5]) / (ssh * (Nsh - 2.0 * Y[4] - Y[5]) + ssh / SIG * (Y[4] - Y[5])) / Lpsh * laser.TM * laser.TM * Y[3] * laser.iss * (1.0 - m[6]) - Y[4] / spt - m[5]
                } else {
                    m[3] =
                        Lac * b * (Y[1] - m0 * Y[2]) * Y[3] - (Y[3] - S0 * lb) / tc_t(
                            t
                        )
                }
            }

            "General" -> {
                if (laser.laserMedium.is_sensitizer == true) {
                    if (laser.laserMedium.levels == 1) {
                        val num4 =
                            a * (1.0 - 2.0 * Y[0]) + sa_wip * Nwion * (1.0 - 2.0 * Y[1] - Y[2] - Y[3] - Y[4])
                        m[0] = a * (1.0 - 2.0 * Y[0]) / num4 * Fp_t(
                            t,
                            -num4 * Lp
                        ) / Nsion - k * Y[0] * (1.0 - Y[2] - Y[1] - Y[3] - Y[4]) - g_31 * Y[0] + A0 * k * Y[1] * (1.0 - Y[0])
                        m[1] =
                            ksi0 * k * Y[0] * (1.0 - Y[2] - Y[1] - Y[3] - Y[4]) - Y[1] * g43 - ksi0 * A0 * k * Y[1] * (1.0 - Y[0]) + g54 * Y[4] + sa_wip * Nwion * (1.0 - 2.0 * Y[1] - Y[2] - Y[3] - Y[4]) / num4 * Fp_t(
                                t,
                                -num4 * Lp
                            ) / Nsion
                        m[2] =
                            Y[1] * g43 - Y[2] * (g32 + g31) - 0.5 * Y[5] * (Y[2] - m0 * Y[3]) * g32 - 0.5 * g32 * Y[5] * (Y[2] - Y[4]) * laser.laserMedium.sa_wi!! / laser.laserMedium.se?.toDouble()!!
                        m[3] =
                            g32 * Y[2] + 0.5 * Y[5] * (Y[2] - m0 * Y[3]) * g32 - g21 * Y[3]
                        m[4] =
                            0.5 * g32 * Y[5] * (Y[2] - Y[4]) * laser.laserMedium.sa_wi!! / laser.laserMedium.se?.toDouble()!! - (g54 + g51) * Y[4]
                        if (laser.shutter == 2 || laser.shutter == 3) {
                            m[8] =
                                exp(-(ssh * (Nsh - 2.0 * Y[6] - Y[7] + (Y[6] - Y[7]) / SIG)) * Lpsh)
                            m[5] =
                                Lac * b * (Y[2] - m0 * Y[3]) * Y[5] + (S0 - Y[5]) / tc_t(
                                    t,
                                    m[8]
                                )
                            m[7] =
                                ssh / SIG * (Y[3] / 2.0 * laser.TM * laser.TM) * laser.iss * (Y[6] - Y[7]) - Y[7] / spt42
                            m[6] =
                                ssh * (Y[3] / 2.0 * laser.TM * laser.TM) * laser.iss * (Nsh - 2.0 * Y[6] - Y[7]) - Y[6] / spt - m[7]
                        }
                        m[5] =
                            Lac * b * (Y[2] - m0 * Y[3] + (Y[2] - Y[4]) * laser.laserMedium.sa_wi / laser.laserMedium.se?.toDouble()!!) * Y[5] - (Y[5] - S0) / tc_t(
                                t
                            )
                    }
                    val num5 = a * (1.0 - 2.0 * Y[0]) + sa_wip * Nwion * (Y[2] - Y[3])
                    m[0] = a * (1.0 - 2.0 * Y[0]) / num5 * Fp_t(
                        t,
                        -num5 * Lp
                    ) / Nsion - k * Y[0] * (1.0 - Y[2] - Y[1]) - g_31 * Y[0] + A0 * k * Y[1] * (1.0 - Y[0])
                    m[1] =
                        ksi0 * k * Y[0] * (1.0 - Y[2] - Y[1]) - Y[1] * (g31 + g32) - ksi0 * A0 * k * Y[1] * (1.0 - Y[0]) + g43 * Y[3]
                    m[2] =
                        g32 * Y[1] - g21 * Y[2] - 0.5 * g21 * (Y[2] - m0 * (1.0 - Y[2] - Y[1])) * Y[4] - 0.5 * g21 * Y[4] * Y[2] * laser.laserMedium.sa_wi!! / laser.laserMedium.se?.toDouble()!! - sa_wip * Nwion * (Y[2] - Y[3]) / num5 * Fp_t(
                            t,
                            -num5 * Lp
                        ) / Nwion
                    m[3] = sa_wip * Nwion * (Y[2] - Y[3]) / num5 * Fp_t(
                        t,
                        -num5 * Lp
                    ) / Nwion - (g43 + g41) * Y[3]
                    if (laser.shutter == 2 || laser.shutter == 3) {
                        m[7] =
                            exp(-(ssh * (Nsh - 2.0 * Y[5] - Y[6] + (Y[5] - Y[6]) / SIG)) * Lpsh)
                        m[4] =
                            Lac * b * (Y[2] - m0 * (1.0 - Y[2] - Y[1])) * Y[4] + (S0 - Y[4]) / tc_t(
                                t,
                                m[7]
                            )
                        m[6] =
                            ssh / SIG * (Y[4] / 2.0 * laser.TM * laser.TM) * laser.iss * (Y[5] - Y[6]) - Y[6] / spt42
                        m[5] =
                            ssh * (Y[4] / 2.0 * laser.TM * laser.TM) * laser.iss * (Nsh - 2.0 * Y[5] - Y[6]) - Y[5] / spt - m[6]
                    }
                    m[4] =
                        Lac * b * (Y[2] - m0 * (1.0 - Y[2] - Y[1])) * Y[4] - (Y[4] - S0) / tc_t(
                            t
                        )
                }
                if (laser.laserMedium.levels == 1) {
                    val num6 =
                        a * (1.0 - 2.0 * Y[0] - Y[1] - Y[2] - Y[3]) + sa_wip * Nwion * (Y[1] - Y[3])
                    m[0] =
                        a * (1.0 - 2.0 * Y[0] - Y[1] - Y[2] - Y[3]) / num6 * Fp_t(
                            t,
                            -num6 * Lp
                        ) / Nwion - g43 / HL * Y[0] + g54 * Y[3]
                    m[1] =
                        g43 * Y[0] - (g32 + g31) * Y[1] - g32 * (Y[1] - m0 * Y[2]) * Y[4] - g32 * Y[4] * Y[1] * laser.laserMedium.sa_wi!! / laser.laserMedium.se?.toDouble()!! - sa_wip * Nwion * (Y[1] - Y[3]) / num6 * Fp_t(
                            t,
                            -num6 * Lp
                        ) / Nwion
                    m[2] =
                        lb * g32 * Y[1] - g21 * Y[2] + g32 * (Y[1] - m0 * Y[2]) * Y[4] + q * g21 * (1.0 - Y[0] - Y[1])
                    m[3] = sa_wip * Nwion * (Y[1] - Y[3]) / num6 * Fp_t(
                        t,
                        -num6 * Lp
                    ) / Nwion - (g54 + g51) * Y[3]
                    if (laser.shutter == 2 || laser.shutter == 3) {
                        m[7] =
                            exp(-(ssh * (Nsh - 2.0 * Y[5] - Y[6] + (Y[5] - Y[6]) / SIG)) * Lpsh)
                        m[4] =
                            Lac * b * (Y[1] - m0 * Y[2]) * Y[4] + (S0 - Y[4]) / tc_t(
                                t,
                                m[7]
                            )
                        m[6] =
                            ssh / SIG * (Y[4] / 2.0 * laser.TM * laser.TM) * laser.iss * (Y[5] - Y[6]) - Y[6] / spt42
                        m[5] =
                            ssh * (Y[4] / 2.0 * laser.TM * laser.TM) * laser.iss * (Nsh - 2.0 * Y[5] - Y[6]) - Y[5] / spt - m[6]

                    }
                    m[4] =
                        Lac * b * (Y[1] - m0 * Y[2]) * Y[4] - (Y[4] - S0 * lb) / tc_t(
                            t
                        )

                }
                val num7 =
                    a * (1.0 - 2.0 * Y[0] - Y[1]) + sa_wip * Nwion * (Y[1] - Y[2])
                m[0] = a * (1.0 - 2.0 * Y[0] - Y[1]) / num7 * Fp_t(
                    t,
                    -num7 * Lp
                ) / Nwion - (g32 + g31) * Y[0] + g43 * Y[2]
                m[1] =
                    g32 * Y[0] - g21 * Y[1] - Y[3] * (Y[1] - m0 * (1.0 - Y[0] - Y[1])) * g21 - g21 * Y[3] * Y[1] * laser.laserMedium.sa_wi!! / laser.laserMedium.se?.toDouble()!! + g43 * Y[2] - sa_wip * Nwion * (Y[1] - Y[2]) / num7 * Fp_t(
                        t,
                        -num7 * Lp
                    ) / Nwion
                m[2] = sa_wip * Nwion * (Y[1] - Y[2]) / num7 * Fp_t(
                    t,
                    -num7 * Lp
                ) / Nwion - (g43 + g41) * Y[2]
                if (laser.shutter == 2 || laser.shutter == 3) {
                    m[6] =
                        exp(-(ssh * (Nsh - 2.0 * Y[4] - Y[5] + (Y[4] - Y[5]) / SIG)) * Lpsh)
                    m[3] =
                        Lac * b * (Y[1] - m0 * (1.0 - Y[0] - Y[1])) * Y[3] + (S0 - Y[3]) / tc_t(
                            t,
                            m[6]
                        )
                    m[5] =
                        ssh / SIG * (Y[3] / 2.0 * laser.TM * laser.TM) * laser.iss * (Y[4] - Y[5]) - Y[5] / spt42
                    m[4] =
                        ssh * (Y[3] / 2.0 * laser.TM * laser.TM) * laser.iss * (Nsh - 2.0 * Y[4] - Y[5]) - Y[4] / spt - m[5]

                }
                m[3] =
                    Lac * b * (Y[1] - m0 * (1.0 - Y[0] - Y[1])) * Y[3] - (Y[3] - S0) / tc_t(
                        t
                    )

            }

            "Yb" -> {
                if (Y[2] / (1.0 - Y[1] - Y[0]) < q)
                    Y[2] = q * (1.0 - Y[1] - Y[0])
                m[0] =
                    g43 * (1.0 - 2.0 * Y[0] - Y[1] - Y[2]) * Fp_t(t) - g43 * Y[0]
                m[1] =
                    g43 * Y[0] - (g32 + g31) * Y[1] - g32 * (Y[1] - m[2] * Y[2]) * Y[3]
                m[2] =
                    lb * g32 * Y[1] - g21 * Y[2] + g32 * (Y[1] - m[2] * Y[2]) * Y[3]
                if (laser.shutter == 2 || laser.shutter == 3) {
                    m[6] =
                        exp(-(ssh * (Nsh - 2.0 * Y[4] - Y[5] + (Y[4] - Y[5]) / SIG)) * Lpsh)
                    m[3] =
                        Lac * b * (Y[1] - m[2] * Y[2]) * Y[3] + (S0 * lb - Y[3]) / tc_t(
                            t,
                            m[6]
                        )
                    m[5] =
                        ssh / SIG * (Y[3] / 2.0 * laser.TM * laser.TM) * laser.iss * (Y[4] - Y[5]) - Y[5] / spt42
                    m[4] =
                        ssh * (Y[3] / 2.0 * laser.TM * laser.TM) * laser.iss * (Nsh - 2.0 * Y[4] - Y[5]) - Y[4] / spt - m[5]
                } else {
                    m[3] =
                        Lac * b * (Y[1] - m[2] * Y[2]) * Y[3] - (Y[3] - S0 * lb) / tc_t(
                            t
                        )
                }
                m[2] += q * (1.0 - m[0] - m[1] - m[2])
            }
        }
    }

    fun ArrCpy(inA: DoubleArray, outA: DoubleArray) {
        val length = inA.size
        for (index in 0 until length) {
            outA[index] = inA[index]
        }
    }

    fun ArrConMult(k: Double, m: DoubleArray) {
        val length = m.size
        for (index in 0 until length) {
            m[index] = k * m[index]
        }
    }

    fun msum(m1: DoubleArray, m2: DoubleArray, res: DoubleArray) {
        val length = m1.size
        for (index in 0 until length) {
            res[index] = m1[index] + m2[index]
        }
    }

    fun adaptstep(step: Double, count: Double, dS: Double, S: Double): Double {
        var newStep = step
        if (newStep == 0.0) {
            newStep = 0.001
        }
        val num1 = minOf(Lo / 29979.2458, count / 2.0)
        if ((S < 1E-16 && dS < 0.0) || S == 0.0) {
            return num1
        }
        val num2 = 0.001
        if (abs(dS) > num2 * S) {
            newStep *= num2 * S / abs(dS)
        }
        if (abs(dS) <= 0.01 * num2 * S) {
            newStep *= 2.0
        }
        if (newStep >= num1) {
            newStep = num1
        }
        return newStep
    }

    fun adaptstep_N(step: Double, count: Double, dS: Double, S: Double): Double {
        var newStep = step
        if (newStep == 0.0) {
            newStep = 0.001
        }
        val num1 = minOf(Lo / 29979.2458, count / 2.0)
        if (S < 1E-06 && dS < 1E-06 && newStep != num1) {
            return num1
        }
        val num2 = 0.001
        if (abs(dS) > num2 * S) {
            newStep = newStep * num2 * S / abs(dS)
        }
        if (abs(dS) <= 0.01 * num2 * S) {
            newStep *= 2.0
        }
        if (newStep >= num1) {
            newStep = num1
        }
        return newStep
    }

    fun NonZeroMatrixAppend(
        M: Array<DoubleArray>,
        M_r: Array<DoubleArray>?
    ): Array<DoubleArray> {
        var num1 = M[0].size - 1
        val length1 = M.size
        val length2 = M[0].size
        val length3: Int = M_r?.size ?: M.size
        val num2: Int = M_r?.get(0)?.size ?: 0
        for (index1 in 0 until length2) {
            var flag = true
            for (index2 in 0 until length1) {
                if (M[index2][index1] != 0.0) {
                    flag = false
                    break
                }
            }
            if (flag) {
                num1 = index1 - 1
                break
            }
        }
        val numArray = Array(length3) { DoubleArray(num2 + num1 + 1) }
        for (index3 in 0 until length3) {
            for (index4 in 0 until num2) {
                numArray[index3][index4] = M_r!![index3][index4]
            }
        }
        if (num2 != 0) {
            for (index5 in 0 until length3) {
                for (index6 in num2 until num2 + num1) {
                    numArray[index5][index6] = M[index5][index6 - num2 + 1]
                }
            }
        } else {
            for (index7 in 0 until length3) {
                for (index8 in num2 until num2 + num1 + 1) {
                    numArray[index7][index8] = M[index7][index8 - num2]
                }
            }
        }
        return numArray
    }

    private fun arrCpy(inA: DoubleArray, outA: DoubleArray) {
        for (index in inA.indices) {
            outA[index] = inA[index]
        }
    }

    fun arrConMult(k: Double, m: DoubleArray) {
        for (index in m.indices) {
            m[index] *= k
        }
    }

    private fun K(
        Y: DoubleArray,
        h: Double,
        t: Double,
        m1: DoubleArray,
        m2: DoubleArray,
        m3: DoubleArray,
        m4: DoubleArray,
        tmp: DoubleArray
    ) {
        this.D(Y, m1, t)
        this.arrCpy(m1, tmp)
        this.arrConMult(h / 2.0, tmp)
        this.msum(Y, tmp, tmp)
        this.D(tmp, m2, t)
        this.arrCpy(m2, tmp)
        this.arrConMult(h / 2.0, tmp)
        this.msum(Y, tmp, tmp)
        this.D(tmp, m3, t)
        this.arrCpy(m3, tmp)
        this.arrConMult(h, tmp)
        this.msum(Y, tmp, tmp)
        this.D(tmp, m4, t)
    }

    fun RkAmp(laser: Laser) {
        val y0: DoubleArray = when (laser.laserMedium.host) {
            "Nd" -> {
                if (laser.shutter == 2 || laser.shutter == 3) {
                    DoubleArray(7)
                } else {
                    DoubleArray(4)
                }
            }

            "Er" -> {
                if (laser.shutter == 2 || laser.shutter == 3) {
                    DoubleArray(7)
                } else {
                    DoubleArray(4)
                }
            }

            "General" -> {
                if (laser.laserMedium.levels != 1) {
                    if (laser.laserMedium.is_sensitizer == false) {
                        if (laser.shutter == 2 || laser.shutter == 3) {
                            DoubleArray(7)
                        } else {
                            DoubleArray(4)
                        }
                    } else {
                        if (laser.shutter == 2 || laser.shutter == 3) {
                            DoubleArray(8)
                        } else {
                            DoubleArray(5)
                        }
                    }
                } else {
                    if (laser.laserMedium.is_sensitizer == false) {
                        if (laser.shutter == 2 || laser.shutter == 3) {
                            DoubleArray(8)
                        } else {
                            DoubleArray(5)
                        }
                    } else {
                        if (laser.shutter == 2 || laser.shutter == 3) {
                            DoubleArray(9)
                        } else {
                            DoubleArray(6)
                        }
                    }
                }
            }

            "Yb" -> {
                if (laser.shutter == 2 || laser.shutter == 3) {
                    DoubleArray(7)
                } else {
                    DoubleArray(4)
                }
            }

            else -> {
                DoubleArray(0)
            }
        }
    }

    fun Rk(): DiffResult {
        val diffResult = DiffResult()
        val y0: DoubleArray = when (laser.laserMedium.host) {
            "Nd" -> {
                if (laser.shutter == 2 || laser.shutter == 3) {
                    DoubleArray(7)
                } else {
                    DoubleArray(4)
                }
            }

            "Er" -> {
                if (laser.shutter == 2 || laser.shutter == 3) {
                    DoubleArray(7)
                } else {
                    DoubleArray(4)
                }
            }

            "General" -> {
                if (laser.laserMedium.levels != 1) {
                    if (laser.laserMedium.is_sensitizer == false) {
                        if (laser.shutter == 2 || laser.shutter == 3) {
                            DoubleArray(7)
                        } else {
                            DoubleArray(4)
                        }
                    } else {
                        if (laser.shutter == 2 || laser.shutter == 3) {
                            DoubleArray(8)
                        } else {
                            DoubleArray(5)
                        }
                    }
                } else {
                    if (laser.laserMedium.is_sensitizer == false) {
                        if (laser.shutter == 2 || laser.shutter == 3) {
                            DoubleArray(8)
                        } else {
                            DoubleArray(5)
                        }
                    } else {
                        if (laser.shutter == 2 || laser.shutter == 3) {
                            DoubleArray(9)
                        } else {
                            DoubleArray(6)
                        }
                    }
                }
            }

            "Yb" -> {
                if (laser.shutter == 2 || laser.shutter == 3) {
                    DoubleArray(7)
                } else {
                    DoubleArray(4)
                }
            }

            else -> {
                DoubleArray(0)
            }
        }

        val length1: Int = y0.size
        val num1 = 0.0
        val tpp: Double = laser.tpp
        val length2: Int = ((tpp - num1) / (this.dt1 * 0.001)).toInt()
        var num2: Int = 0
        val num3: Double = this.dt1 * 0.001
        val num4: Double = (laser.configuration.roc!! - laser.Ffrom) / (laser.Fto - laser.Ffrom)

        when (laser.laserMedium.host) {
            "Nd" -> {
                val numArray1 = doubleArrayOf(
                    2.47384207521793E-19,
                    2.28516644012424E-19,
                    3.99697220603622E-20
                )
                var num6 = 1.0
                for (index in numArray1.indices) {
                    y0[index] = exp(-numArray1[index] / (1.38E-23 * laser.laserMedium.ot!!))
                    num6 += y0[index]
                }
                for (index in numArray1.indices) {
                    y0[index] /= num6
                }
                y0[3] = this.S0
            }

            "Er" -> {
                val numArray2 = doubleArrayOf(
                    2.11329E-19,
                    2.11329E-19,
                    1.2899E-19
                )
                var num7 = 1.0
                for (index in numArray2.indices) {
                    y0[index] = exp(-numArray2[index] / (1.38E-23 * laser.laserMedium.ot!!))
                    num7 += y0[index]
                }
                for (index in numArray2.indices) {
                    y0[index] /= num7
                }
                y0[3] = this.S0
            }

            "General" -> {
                val num8 = 6.626068E-34
                val num9 = 299800000.0
                if (laser.laserMedium.is_sensitizer == true) {
                    if (laser.laserMedium.levels == 1) {
                        val numArray3 = doubleArrayOf(
                            laser.laserMedium.e2s?.toDouble()!!,
                            laser.laserMedium.e4w?.toDouble()!!,
                            laser.laserMedium.e3w?.toDouble()!!,
                            laser.laserMedium.e2w?.toDouble()!!,
                            laser.laserMedium.e3w.toDouble() + laser.laserMedium.e2s.toDouble()
                        )
                        for (index in numArray3.indices) {
                            numArray3[index] = num8 * num9 * numArray3[index] * 100.0
                        }
                        var num10 = 1.0
                        for (index in numArray3.indices) {
                            y0[index] =
                                exp(-numArray3[index] / (1.38E-23 * laser.laserMedium.ot!!))
                            num10 += y0[index]
                        }
                        for (index in numArray3.indices) {
                            y0[index] = y0[index] / num10
                        }
                        y0[5] = this.S0
                    } else {
                        val numArray4 = doubleArrayOf(
                            laser.laserMedium.e2s?.toDouble()!!,
                            laser.laserMedium.e3w?.toDouble()!!,
                            laser.laserMedium.e2w?.toDouble()!!,
                            laser.laserMedium.e2w.toDouble() + laser.laserMedium.e2s.toDouble()
                        )
                        for (index in numArray4.indices) {
                            numArray4[index] = num8 * num9 * numArray4[index] * 100.0
                        }
                        var num11 = 1.0
                        for (index in numArray4.indices) {
                            y0[index] =
                                Math.exp(-numArray4[index] / (1.38E-23 * laser.laserMedium.ot!!))
                            num11 += y0[index]
                        }
                        for (index in numArray4.indices) {
                            y0[index] = y0[index] / num11
                        }
                        y0[4] = this.S0
                    }
                } else {
                    if (laser.laserMedium.levels == 1) {
                        val numArray5 = doubleArrayOf(
                            laser.laserMedium.e4w?.toDouble()!!,
                            laser.laserMedium.e3w?.toDouble()!!,
                            laser.laserMedium.e2w?.toDouble()!!,
                            laser.laserMedium.e3w.toDouble() + laser.laserMedium.e4w.toDouble()
                        )
                        for (index in numArray5.indices) {
                            numArray5[index] = num8 * num9 * numArray5[index] * 100.0
                        }
                        var num12 = 1.0
                        for (index in numArray5.indices) {
                            y0[index] =
                                exp(-numArray5[index] / (1.38E-23 * laser.laserMedium.ot!!))
                            num12 += y0[index]
                        }
                        for (index in numArray5.indices) {
                            y0[index] = y0[index] / num12
                        }
                        y0[4] = this.S0
                    } else {
                        val numArray6 = doubleArrayOf(
                            laser.laserMedium.e3w?.toDouble()!!,
                            laser.laserMedium.e2w?.toDouble()!!,
                            laser.laserMedium.e2w.toDouble() + laser.laserMedium.e3w.toDouble()
                        )
                        for (index in numArray6.indices) {
                            numArray6[index] = num8 * num9 * numArray6[index] * 100.0
                        }
                        var num13 = 1.0
                        for (index in numArray6.indices) {
                            y0[index] =
                                exp(-numArray6[index] / (1.38E-23 * laser.laserMedium.ot!!))
                            num13 += y0[index]
                        }
                        for (index in numArray6.indices) {
                            y0[index] = y0[index] / num13
                        }
                        y0[3] = this.S0
                    }
                }
            }

            "Yb" -> {
                val numArray7 = doubleArrayOf(
                    2.11045E-19,
                    2.05145E-19,
                    1.21574E-20
                )
                var num14 = 1.0
                for (index in numArray7.indices) {
                    y0[index] = exp(-numArray7[index] / (1.38E-23 * laser.laserMedium.ot!!))
                    num14 += y0[index]
                }
                for (index in numArray7.indices) {
                    y0[index] = y0[index] / num14
                }
                laser.laserMedium.q = y0[2]
                this.q = y0[2]
                y0[3] = this.S0
            }

            else -> {
            }
        }

        if (laser.shutter == 2 || laser.shutter == 3) {
            y0[length1 - 3] = 0.0
            y0[length1 - 2] = 0.0
            y0[length1 - 1] = laser.qSwitch.spt0!!
        }
        var M = Array(length1 + 1) { DoubleArray(length2) }
        val m1 = DoubleArray(length1 + 1)
        m1[0] = num1
        for (index in 1 until length1 + 1) {
            m1[index] = y0[index - 1]
        }

        M = this.setColumn(M, m1, 0)

        val m2 = DoubleArray(length1 + 1)
        val numArray8 = DoubleArray(length1)
        var newSS = DoubleArray(length1)
        var num15 = -1
        var num16 = this.dt1 * 0.001
        var j = 0
        var num17 = num16
        if (laser.shutter == 1 && laser.qSwitch.AQStype != 2 && laser.qSwitch.AQStype != 3) {
            num17 *= 10.0
        }
        val num18 = this.Lo / 29979.2458 / 10.0
        var num19 = min(this.Lo / 29979.2458, num16)
        var t = M[0][0]
        var flag = false
        var M_r: Array<DoubleArray>? = null
        //val snum = Main.Laser1.snum
        var tmp = DoubleArray(length1)
        var numArray9 = DoubleArray(length1)
        var numArray10 = DoubleArray(length1)
        var numArray11 = DoubleArray(length1)
        var numArray12 = DoubleArray(length1)
        var numArray13 = DoubleArray(length1)
        var numArray14 = DoubleArray(length1)
        while (t < tpp) {
            if (laser.shutter != 0) {
                val index1: Int = if (laser.shutter != 1) length1 - 3 else length1
                if (M[index1][j] > S0 * 5000.0 && laser.shutter != 1 || laser.shutter == 1 && Ts(t) != sT0) {
                    if (num16 != num18 && this.ThresholdCheck(newSS, t, m2[length1])) {
                        num16 = num18
                        M_r = NonZeroMatrixAppend(M, M_r)
                        M = emptyArray()
                        val length3 =
                            if ((tpp - t) / num16 + 1000.0 >= Int.MAX_VALUE) 134217728 / (length1 + 1) else ((tpp - t) / num16).toInt() + 1000
                        M = Array(length1 + 1) { DoubleArray(length3) }
                        for (index2 in 0 until length1 + 1)
                            M[index2][0] = M_r[index2][M_r[0].size - 1]
                        j = 0
                    }
                } else if (num16 != num17) {
                    num16 = num17
                    M_r = NonZeroMatrixAppend(M, M_r)
                    M = emptyArray()
                    val length4 = ((tpp - t) / num16).toInt() + 1000
                    M = Array(length1 + 1) { DoubleArray(length4) }
                    for (index3 in 0 until length1 + 1)
                        M[index3][0] = M_r[index3][M_r[0].size - 1]
                    j = 0
                }
            }
            val num21 = t + num16
            ColumnTo1DArray(M, newSS, j, 1, length1)
            while (t < num21) {
                val num22 = num19
                K(newSS, num19, t, numArray9, numArray10, numArray12, numArray13, tmp)
                ArrConMult(2.0, numArray10)
                msum(numArray10, numArray9, numArray11)
                ArrConMult(2.0, numArray12)
                msum(numArray13, numArray12, numArray14)
                msum(numArray11, numArray14, numArray8)
                ArrConMult(num19 / 6.0, numArray8)
                num19 = if (laser.shutter == 2 || laser.shutter == 3) {
                    val num25 =
                        adaptstep(num19, num16, numArray8[length1 - 4], newSS[length1 - 4])
                    val num26 = min(adaptstep_N(num25, num16, numArray8[1], newSS[1]), num25)
                    min(adaptstep_N(num26, num16, numArray8[0], newSS[0]), num26)
                } else {
                    if (laser.laserMedium.host != "Yb") {
                        min(
                            adaptstep(num19, num16, numArray8[length1 - 1], newSS[length1 - 1]),
                            adaptstep(num19, num16, numArray8[length1 - 2], newSS[length1 - 2])
                        )
                    } else {
                        min(
                            adaptstep(num19, num16, numArray8[length1 - 1], newSS[length1 - 1]),
                            adaptstep(num19, num16, numArray8[length1 - 3], newSS[length1 - 3])
                        )
                    }
                }

                if (num22 <= num19) {
                    msum(newSS, numArray8, newSS)
                    if (laser.laserMedium.host == "General") {
                        if (laser.shutter == 2 || laser.shutter == 3) {
                            if (newSS[length1 - 5] < 0.0) {
                                newSS[length1 - 5] = 0.0
                            }
                        } else if (newSS[length1 - 2] < 0.0) {
                            newSS[length1 - 2] = 0.0
                        }
                    }
                    if (laser.laserMedium.host == "Yb" && newSS[2] / (1.0 - newSS[1] - newSS[0]) < q) {
                        newSS[2] = q * (1.0 - newSS[1] - newSS[0])
                    }
                    t += num19
                }
            }
            m2[0] = t
            for (index in 1 until length1 + 1) {
                m2[index] = if (newSS[index - 1] <= 0.0) 0.0 else newSS[index - 1]
            }
            if (laser.shutter == 2 || laser.shutter == 3) {
                m2[length1] =
                    exp(-(ssh * (Nsh - 2.0 * m2[4] - m2[5] + (m2[4] - m2[5]) / SIG)) * Lpsh)
            }

            if (j == 1000) {
                if (this.step_ch(snum)) {
                    M = setColumn(M, m2, j + 1)
                } else {
                    val num27 = "Some error occurred."
                }
            } else {
                M = setColumn(M, m2, j + 1)
            }
            if (num2 > 10000) {
                var num28 = 0
                while (num28 < 10000) {
                    ++num28
                }
            }

            if (num15 == -1) {
                if (ThresholdCheck(newSS, t, m2[length1])) {
                    num15 = j
                }
                diffResult.ith = num15
            } else {
                flag = true
            }
            ++j
        }

        if (!flag) {
            val num30 = "No lasing threshold"
        } else {
            val SS = this.NonZeroMatrixAppend(M, M_r)
            M = emptyArray()
            diffResult.Input(SS)
        }
        return diffResult
    }
}