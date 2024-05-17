package org.example.diploma.laser

import android.util.Log
import kotlin.math.exp
import kotlin.math.ln

class DiffResult() {

//    var g_31: Double = laser.laserMedium.sa_wi!!
//    var ith

    var n: Int = 0
    var completed: Boolean = false
    var resultMatrix: Array<DoubleArray> = emptyArray()
    var t: DoubleArray = doubleArrayOf()
    var n2: DoubleArray = doubleArrayOf()
    var n3: DoubleArray = doubleArrayOf()
    private var n1Index: Int = 0
    private var n2Index: Int = 0
    private var n3Index: Int = 0
    private var sinIndex: Int = 0
    var Sin: DoubleArray = doubleArrayOf()
    var Sout: DoubleArray = doubleArrayOf()
    var T_Sh: DoubleArray = doubleArrayOf()
    var U: DoubleArray = doubleArrayOf()
    var Loss: DoubleArray = doubleArrayOf()
    var P: DoubleArray = doubleArrayOf()
    lateinit var f: DoubleArray
    lateinit var fx: DoubleArray
    lateinit var f2d: Array<DoubleArray>
    lateinit var fXf2d: Array<DoubleArray>
    var fXMax: Double = 0.0
    var maxUf: Double = 0.0
    var maxPf: Double = 0.0
    var maxTshf: Double = 0.0
    var maxLossf: Double = 0.0
    var ith: Int = 0
    private var dt: Double = 0.0
    var Imax: Double = 0.0
    var Efg: Double = 0.0
    var AOP: Double = 0.0
    var Hf: Double = 0.0
    var mSi: Int = 0
    var stsi: Int = 0
    var m1i: Int = 0
    var m2i: Int = 0
    var tm: Double = 0.0
    var Tg: Double = 0.0
    var EQg: Double = 0.0
    var EQg2: Double = 0.0
    var contrast: Double = 0.0
    var HQ: Double = 0.0
    var Est: Double = 0.0
    var Wgmax: Double = 0.0
    private var roc: Double = 0.0

    fun Input(SS: Array<DoubleArray>, diffFunc: DiffFunc): Boolean {
        roc = diffFunc.Roc
        resultMatrix = SS
        val length1 = SS.size
        val length2 = SS[0].size
        Log.d("laser", "lol")
        n = length2
        t = DoubleArray(length2)
        n3 = DoubleArray(length2)
        n2 = DoubleArray(length2)
        Sin = DoubleArray(length2)
        Sout = DoubleArray(length2)
        U = DoubleArray(length2)
        Loss = DoubleArray(length2)
        P = DoubleArray(length2)
        if (diffFunc.shutter != 0) {
            T_Sh = DoubleArray(length2)
        }
        when (diffFunc.host) {
            "Nd", "Er", "Yb" -> {
                n3Index = 2
                n2Index = 3
                sinIndex = 4
            }

            "General" -> {
                if (diffFunc.levels == 1) {
                    if (diffFunc.is_sensitizer) {
                        n3Index = 3
                        n2Index = 4
                        sinIndex = 6
                    } else {
                        n3Index = 2
                        n2Index = 3
                        sinIndex = 5
                    }
                } else if (diffFunc.is_sensitizer == true) {
                    n3Index = 2
                    n2Index = 3
                    sinIndex = 5
                } else {
                    n3Index = 1
                    n2Index = 2
                    sinIndex = 4
                }
            }
        }

        for (index in 0 until length2) {
            t[index] = SS[0][index]
            n3[index] = SS[n3Index][index]
            n2[index] = SS[n2Index][index]
            Sin[index] = SS[sinIndex][index]
            when (diffFunc.host) {
                "Er" -> {
                    U[index] = SS[n2Index][index] - (1.0 - SS[n2Index][index] - SS[n3Index][index])
                }
                "Nd" -> {
                    U[index] = SS[n3Index][index] - SS[n2Index][index]
                }
                "General" -> {
                    U[index] = if (diffFunc.levels != 1) {
                        SS[n2Index][index] - (1.0 - SS[n2Index][index] - SS[n3Index][index])
                    } else {
                        SS[n3Index][index] - SS[n2Index][index]
                    }
                }
                "Yb" -> {
                    U[index] = SS[n3Index][index] - SS[n2Index][index]
                }
            }
            when (diffFunc.shutter) {
                1 -> T_Sh[index] = diffFunc.Ts(t[index])
                2 -> T_Sh[index] = SS[length1 - 1][index]
                3 -> T_Sh[index] = SS[length1 - 1][index] * diffFunc.Ts(t[index])
            }

            when (diffFunc.shutter) {
                0 -> {
                    Loss[index] = 1.0 - exp(-(-ln(roc * (1.0 - diffFunc.gc)) + 2.0 * diffFunc.ga_t(t[index]) * diffFunc.la))
                }
                1 -> {
                    when (diffFunc.AQStype) {
                        0 -> {
                            Loss[index] = 1.0 - exp(-(-ln(roc * T_Sh[index] * T_Sh[index] * (1.0 - diffFunc.gc)) + 2.0 * diffFunc.ga_t(t[index]) * diffFunc.la))
                        }
                        1 -> {
                            Loss[index] = 1.0 - exp(-(-ln(roc * T_Sh[index] * (1.0 - diffFunc.gc)) + 2.0 * diffFunc.ga_t(t[index]) * diffFunc.la))
                        }
                        2 -> {
                            Loss[index] = 1.0 - exp(-(-ln((1.0 - T_Sh[index]) / (1.0 + T_Sh[index]) * (1.0 - diffFunc.gc)) + 2.0 * diffFunc.ga_t(t[index]) * diffFunc.la))
                        }
                        3 -> {
                            Loss[index] = 1.0 - exp(-(-ln(T_Sh[index] / (2.0 - T_Sh[index]) * (1.0 - diffFunc.gc)) + 2.0 * diffFunc.ga_t(t[index]) * diffFunc.la))
                        }
                    }
                }
                2 -> {
                    Loss[index] = 1.0 - exp(-(-ln(roc * T_Sh[index] * T_Sh[index] * (1.0 - diffFunc.gc)) + 2.0 * diffFunc.ga_t(t[index]) * diffFunc.la))
                }
                3 -> {
                    when (diffFunc.AQStype) {
                        0 -> {
                            Loss[index] = 1.0 - exp(-(-ln(roc * T_Sh[index] * T_Sh[index] * (1.0 - diffFunc.gc)) + 2.0 * diffFunc.ga_t(t[index]) * diffFunc.la))
                        }
                        1 -> {
                            val num3 = diffFunc.Ts(t[index])
                            Loss[index] = 1.0 - exp(-(-ln(T_Sh[index] * T_Sh[index] / diffFunc.Ts(t[index]) * (1.0 - diffFunc.gc)) + 2.0 * diffFunc.ga_t(t[index]) * diffFunc.la))
                        }
                        2 -> {
                            val num4 = diffFunc.Ts(t[index])
                            Loss[index] = 1.0 - exp(-(-ln(T_Sh[index] * T_Sh[index] / (1.0 - num4 * num4) * (1.0 - diffFunc.gc)) + 2.0 * diffFunc.ga_t(t[index]) * diffFunc.la))
                        }
                        3 -> {
                            val num4 = diffFunc.Ts(t[index])
                            Loss[index] = 1.0 - exp(-(-ln(T_Sh[index] * T_Sh[index] / (num4 * (2.0 - num4)) * (1.0 - diffFunc.gc)) + 2.0 * diffFunc.ga_t(t[index]) * diffFunc.la))
                        }
                    }
                }

            }
            Sout[index] = when {
                (diffFunc.AQStype != 2 || (diffFunc.shutter != 1 && diffFunc.shutter != 3)) -> {
                    when {
                        (diffFunc.AQStype != 3 || (diffFunc.shutter != 1 && diffFunc.shutter != 3)) -> {
                            Sin[index] * (1.0 - roc) / (1.0 + roc)
                        }
                        else -> {
                            Sin[index] * (1.0 - diffFunc.Ts(t[index]))
                        }
                    }
                }
                else -> {
                    Sin[index] * diffFunc.Ts(t[index])
                }
            }
        }
        val num5 = U.maxOrNull() ?: 0.0
        for (index in 0 until length2) {
            U[index] = if (U[index] >= 0.0) U[index] / num5 else 0.0
        }
        for (index in 0 until length2) {
            P[index] = diffFunc.Fp_t(t[index]) * diffFunc.Isp * diffFunc.Epph / 1E-06 / diffFunc.Fav / diffFunc.Hc * diffFunc.Ap
        }
        if (ith < 0) {
            ith = n / 2
            dt = t[ith + 1] - t[ith]
        } else {
            dt = t[ith + 1] - t[ith]
        }
        Sin.maxOrNull()
        val num7 = Sout.maxOrNull() ?: 0.0
        for (index in 0 until length2) {
            if (Sout[index] == num7) {
                mSi = index
            }
        }
        for (index in length2 - 2 downTo 1) {
            if (Sout[index] >= num7 * 0.2 && Sout[index] >= Sout[index - 1] && Sout[index] >= Sout[index + 1]) {
                mSi = index
            }
        }
        if (diffFunc.shutter == 1) {
            for (index in 0 until length2) {
                if (diffFunc.sts < t[index]) {
                    stsi = index - 1
                    break
                }
            }
        } else {
            var tempMsi = mSi
            while (tempMsi >= 0) {
                if (Sout[tempMsi] < num7 / exp(3.0)) {
                    stsi = tempMsi - 1
                    break
                }
                tempMsi--
            }
        }
        for (stsi in this.stsi..this.mSi) {
            if (Sout[stsi] / Sout[mSi] > 0.5) {
                m1i = stsi - 1
                break
            }
        }
        for (mSi in mSi until length2) {
            if (Sout[mSi] / Sout[mSi] < 0.5) {
                m2i = mSi - 1
                break
            }
        }
        Calculate(diffFunc)
        return true
    }

    fun Calculate(diffFunc: DiffFunc){
        val num1 = this.Sin.maxOrNull() ?: 0.0
        val num2 = this.Sout.maxOrNull() ?: 0.0
        Imax = diffFunc.E0ph * num1 * diffFunc.Iss
        Wgmax = diffFunc.Ag * Imax * 1000000.0 * num2 / num1
        val num3 = dt * 1E-06
        Efg = 0.0
        for (ith in this.ith until this.n - 1) {
            Efg += (t[ith + 1] - t[ith]) * 1E-06 * Wgmax / num2 * (Sout[ith] + Sout[ith + 1]) / 2.0
        }
        AOP = if (diffFunc.shutter != 0) {
            Efg / ((diffFunc.tpp - t[mSi]) * 1E-06)
        } else {
            Efg / ((diffFunc.tp - t[mSi]) * 1E-06)
        }
        Hf = Efg / (diffFunc.Wp * diffFunc.tp * 1E-06)
        tm = t[mSi]
        if (diffFunc.shutter != 0) {
            tm = if (diffFunc.shutter != 1) {
                t[mSi]
            } else {
                (t[mSi] - diffFunc.sts) * 1000.0
            }
        }
        Tg = (t[m2i] + t[m2i + 1] - t[m1i] - t[m1i + 1]) / 2.0 * 1000.0
        var num4 = 0
        var num5 = t.size - 1
        EQg = 0.0
        for (index in 1..mSi) {
            if (Sout[index] / num2 > exp(-5.0)) {
                num4 = index - 1
                break
            }
        }

        for (mSi in mSi until Sout.size) {
            if (Sout[mSi] / num2 < exp(-5.0)) {
                num5 = mSi - 1
                break
            }
        }

        for (index in num4 until num5) {
            EQg += (t[index + 1] - t[index]) * 1E-06 * Wgmax / num2 * (Sout[index] + Sout[index + 1]) / 2.0
        }

        HQ = EQg / (diffFunc.Wp * diffFunc.tp * 1E-06)

        when (diffFunc.host) {
            "Er" -> {
                Est = if (!diffFunc.cyl) {
                    0.5 * (2.0 * n2[stsi] + n3[stsi] - 1.0) * diffFunc.Ner * diffFunc.E0ph * diffFunc.la * diffFunc.lb * diffFunc.Ld
                } else {
                    0.5 * (2.0 * n2[stsi] + n3[stsi] - 1.0) * diffFunc.Ner * diffFunc.E0ph * diffFunc.la * Math.PI * diffFunc.DIA * diffFunc.DIA / 4.0
                }
            }
            "Nd" -> {
                Est = if (!diffFunc.cyl) {
                    (n3[stsi] - n2[stsi]) * diffFunc.Nd * diffFunc.E0ph * diffFunc.la * diffFunc.lb * diffFunc.Ld
                } else {
                    (n3[stsi] - n2[stsi]) * diffFunc.Nd * diffFunc.E0ph * diffFunc.la * Math.PI * diffFunc.DIA * diffFunc.DIA / 4.0
                }
            }
            "General" -> {
                Est = if (diffFunc.levels != 1) {
                    if (!diffFunc.cyl) {
                        0.5 * (2.0 * n2[stsi] - 1.0 + n3[stsi]) * diffFunc.Nwion * diffFunc.E0ph * diffFunc.la * diffFunc.lb * diffFunc.Ld
                    } else {
                        0.5 * (2.0 * n2[stsi] - 1.0 + n3[stsi]) * diffFunc.Nwion * diffFunc.E0ph * diffFunc.la * Math.PI * diffFunc.DIA * diffFunc.DIA / 4.0
                    }
                } else {
                    if (!diffFunc.cyl) {
                        (n3[stsi] - n2[stsi]) * diffFunc.Nwion * diffFunc.E0ph * diffFunc.la * diffFunc.lb * diffFunc.Ld
                    } else {
                        (n3[stsi] - n2[stsi]) * diffFunc.Nwion * diffFunc.E0ph * diffFunc.la * Math.PI * diffFunc.DIA * diffFunc.DIA / 4.0
                    }
                }
            }
            "Yb" -> {
                Est = if (!diffFunc.cyl) {
                    (n3[stsi] - n2[stsi]) * diffFunc.Nwion * diffFunc.E0ph * diffFunc.la * diffFunc.lb * diffFunc.Ld
                } else {
                    (n3[stsi] - n2[stsi]) * diffFunc.Nwion * diffFunc.E0ph * diffFunc.la * Math.PI * diffFunc.DIA * diffFunc.DIA / 4.0
                }
            }
            else -> {
                Log.d("heherror", "error hehe")
            }
        }

        if (diffFunc.shutter != 2 && diffFunc.shutter != 3) {
            return
        }

        var num6 = 0
        for (mSi in mSi until Sout.size) {
            if (Sout[mSi] < Sout[this.mSi] * exp(-2.0)) {
                num6 = mSi
                break
            }
        }
        if (num6 != 0) {
            var EQg2 = 0.0
            for (index1 in (num6 + 2) until (Sout.size - 2)) {
                if (Sout[index1] > Sout[index1 - 1] &&
                    Sout[index1] > Sout[index1 - 2] &&
                    Sout[index1] < Sout[index1 + 1] &&
                    Sout[index1] < Sout[index1 + 2] &&
                    Sout[index1] / Sout[mSi] >= 0.01
                ) {
                    val num7 = num6
                    var num8 = t.size - 1
                    for (index2 in num6..index1) {
                        if (Sout[index2] / Sout[index1] > exp(-2.0)) {
                            val num9 = index2 - 1
                            break
                        }
                    }
                    for (mSi in mSi until Sout.size) {
                        if (Sout[mSi] / Sout[index1] < exp(-2.0)) {
                            val num10 = mSi + 1
                            break
                        }
                    }
                    for (index3 in index1 until t.size) {
                        if (Sout[index3] / Sout[index1] < exp(-2.0)) {
                            num8 = index3 + 1
                            break
                        }
                    }
                    for (index4 in num7 until num8) {
                        EQg2 += (t[index4 + 1] - t[index4]) * 1E-06 / 2.0 * Wgmax / num2 * (Sout[index4] + Sout[index4 + 1])
                    }
                    break
                }
            }
        }
        contrast = if (EQg2 != 0.0) EQg / EQg2 else 0.0
    }
}