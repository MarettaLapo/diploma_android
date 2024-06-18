package org.example.diploma

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import org.example.diploma.database.amplifier.AmplifierEntity
import org.example.diploma.database.amplifier.AmplifierRepository
import org.example.diploma.database.configuration.ConfigurationEntity
import org.example.diploma.database.configuration.ConfigurationRepository
import org.example.diploma.database.giantPulse.GiantPulseEntity
import org.example.diploma.database.giantPulse.GiantPulseRepository
import org.example.diploma.database.host.HostEntity
import org.example.diploma.database.host.HostRepository
import org.example.diploma.database.laserMedium.LaserMediumEntity
import org.example.diploma.database.laserMedium.LaserMediumRepository
import org.example.diploma.database.laserOutput.LaserOutputEntity
import org.example.diploma.database.laserOutput.LaserOutputRepository
import org.example.diploma.database.optimization.OptimizationEntity
import org.example.diploma.database.optimization.OptimizationRepository
import org.example.diploma.database.output.OutputEntity
import org.example.diploma.database.output.OutputRepository
import org.example.diploma.database.pump.PumpEntity
import org.example.diploma.database.pump.PumpRepository
import org.example.diploma.database.qSwitch.QSwitchEntity
import org.example.diploma.database.qSwitch.QSwitchRepository
import org.example.diploma.database.save.SaveEntity
import org.example.diploma.database.save.SaveRepository
import org.example.diploma.laser.DiffFunc
import org.example.diploma.laser.DiffResult
import org.example.diploma.laser.Laser
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.math.exp
import kotlin.math.ln
import kotlin.math.sin
import kotlin.math.sqrt

class MainViewModel(
    val amplifierRepository: AmplifierRepository,
    val configurationRepository: ConfigurationRepository,
    val hostRepository: HostRepository,
    val laserMediumRepository: LaserMediumRepository,
    val optimizationRepository: OptimizationRepository,
    val pumpRepository: PumpRepository,
    val qSwitchRepository: QSwitchRepository,
    val saveRepository: SaveRepository,
    val outputRepository: OutputRepository,
    val laserOutputRepository: LaserOutputRepository,
    val giantPulseRepository: GiantPulseRepository
) : ViewModel() {

    val laserDataFlow: MutableStateFlow<Laser>

    var laserMedium: LaserMediumEntity? = null
    var configuration: ConfigurationEntity? = null
    var pump: PumpEntity? = null
    var qSwitch: QSwitchEntity? = null
    var amplifier: AmplifierEntity? = null

    val isCylinder: MutableStateFlow<Boolean>
    val pumpScheme: MutableStateFlow<Int>
    val pumpType: MutableStateFlow<Int>
    val isQSwitch: MutableStateFlow<Boolean>
    val isAQS: MutableStateFlow<Boolean>
    val isPQS: MutableStateFlow<Boolean>
    val qSwitchType: MutableStateFlow<Int>
    val qSwitchFrontType: MutableStateFlow<Int>
    val qSwitchMode: MutableStateFlow<Int>
    val isAmplifier: MutableStateFlow<Boolean>
    val waveform: MutableStateFlow<Int>

    val shutter: MutableStateFlow<Int>

    var output: MutableStateFlow<OutputEntity>
    var giantPulse: MutableStateFlow<GiantPulseEntity?>
    var laserOutput: MutableStateFlow<LaserOutputEntity>

    var hh = false

    var flag = false
    var hehe = 0

    init {
        // Инициализируем laserDataFlow при создании экземпляра MainViewModel
        val defaultLaser = Laser(
            laserMedium = LaserMediumEntity(
                null, null, null, null, null, null, null,
                null, null, null, null, null, null, null, null, null, null, null,
                null, null, null, null, null, null, null, null, null, null, null,
                null, null, null, null, null, null, null, null, null, null, null, null, null, null
            ),
            configuration = ConfigurationEntity(
                null, null, null, null, null,
                null, null, null, null, null, null,
            ),
            pump = PumpEntity(
                null, null, null, null, null, null,
                null, null, null, null, null, null
            ),
            qSwitch = QSwitchEntity(
                null, null, null, null, null, null,
                null, null, null, null, null, null, null, null, null,
                null, null, null, null, null, null, null, null, null, null,
            ),
            amplifier = AmplifierEntity(null, null, null, null, null, null, null),
        )
        isCylinder = MutableStateFlow(false)
        pumpScheme = MutableStateFlow(1)
        pumpType = MutableStateFlow(0)
        isQSwitch = MutableStateFlow(false)
        isAQS = MutableStateFlow(false)
        isPQS = MutableStateFlow(false)
        qSwitchType = MutableStateFlow(0)
        qSwitchFrontType = MutableStateFlow(0)
        qSwitchMode = MutableStateFlow(0)
        isAmplifier = MutableStateFlow(false)
        waveform = MutableStateFlow(1)
        shutter = MutableStateFlow(0)

        val lo = LaserOutputEntity(null, null, null, null, null, null)
        val ou = OutputEntity(
            null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null,
            null, null, null
        )

        val gp = GiantPulseEntity(
            null, null, null, null, null, null,
            null, null
        )
        laserOutput = MutableStateFlow(lo)
        output = MutableStateFlow(ou)
        giantPulse = MutableStateFlow(gp)

        laserDataFlow = MutableStateFlow(defaultLaser)
    }

    val allHosts = hostRepository.getAllHosts()

    val allSaves = saveRepository.getAllSaves()


    fun selectedHost(host: HostEntity) {
        viewModelScope.launch {
            val laserMediumData = laserMediumRepository.getLaserMediumData(host.laserMediumId)

            val configurationData =
                configurationRepository.getConfigurationData(host.configurationId)

            val pumpData = pumpRepository.getPumpData(host.pumpId)

            val qSwitchData = qSwitchRepository.getQSwitchData(host.qSwitchId)

            val amplifierData = amplifierRepository.getAmplifierData(host.amplifierId)


            laserMedium = laserMediumData.firstOrNull()
            configuration = configurationData.firstOrNull()
            pump = pumpData.firstOrNull()
            qSwitch = qSwitchData.firstOrNull()
            amplifier = amplifierData.firstOrNull()

            // Создаем объект Laser с полученными данными
            val newLaser = Laser(
                laserMedium!!,
                configuration!!,
                pump!!,
                qSwitch!!,
                amplifier!!,
            )
            newLaser.timestamp = System.currentTimeMillis()
            isCylinder.value = configuration!!.isCylinder!!
            pumpScheme.value = pump!!.scheme!!
            pumpType.value = pump!!.ptypeId!!
            isQSwitch.value = qSwitch!!.isQSwitch!!
            isAQS.value = qSwitch!!.isAQS!!
            isPQS.value = qSwitch!!.isPQS!!
            qSwitchType.value = qSwitch!!.AQStype!!
            qSwitchFrontType.value = qSwitch!!.sFrontType!!
            qSwitchMode.value = qSwitch!!.mode!!
            isAmplifier.value = amplifier!!.isUse!!
            waveform.value = amplifier!!.waveform!!

            shutter.value = when {
                qSwitch!!.isAQS!! && qSwitch!!.isPQS!! && qSwitch!!.isQSwitch!! -> 3
                qSwitch!!.isPQS!! && qSwitch!!.isQSwitch!! -> 2
                qSwitch!!.isAQS!! && qSwitch!!.isQSwitch!! -> 1
                else -> 0 //no qswitch
            }

            laserDataFlow.value = newLaser
        }
    }

    fun selectedSave(host: SaveEntity) {
        viewModelScope.launch {
            val laserMediumData = laserMediumRepository.getLaserMediumData(host.laserMediumId)

            val configurationData =
                configurationRepository.getConfigurationData(host.configurationId)

            val pumpData = pumpRepository.getPumpData(host.pumpId)

            val qSwitchData = qSwitchRepository.getQSwitchData(host.qSwitchId)

            val amplifierData = amplifierRepository.getAmplifierData(host.amplifierId)


            laserMedium = laserMediumData.firstOrNull()
            configuration = configurationData.firstOrNull()
            pump = pumpData.firstOrNull()
            qSwitch = qSwitchData.firstOrNull()
            amplifier = amplifierData.firstOrNull()

            // Создаем объект Laser с полученными данными
            val newLaser = Laser(
                laserMedium!!,
                configuration!!,
                pump!!,
                qSwitch!!,
                amplifier!!,
            )
            newLaser.timestamp = System.currentTimeMillis()
            isCylinder.value = configuration!!.isCylinder!!
            pumpScheme.value = pump!!.scheme!!
            pumpType.value = pump!!.ptypeId!!
            isQSwitch.value = qSwitch!!.isQSwitch!!
            isAQS.value = qSwitch!!.isAQS!!
            isPQS.value = qSwitch!!.isPQS!!
            qSwitchType.value = qSwitch!!.AQStype!!
            qSwitchFrontType.value = qSwitch!!.sFrontType!!
            qSwitchMode.value = qSwitch!!.mode!!
            isAmplifier.value = amplifier!!.isUse!!
            waveform.value = amplifier!!.waveform!!

            shutter.value = when {
                qSwitch!!.isAQS!! && qSwitch!!.isPQS!! && qSwitch!!.isQSwitch!! -> 3
                qSwitch!!.isPQS!! && qSwitch!!.isQSwitch!! -> 2
                qSwitch!!.isAQS!! && qSwitch!!.isQSwitch!! -> 1
                else -> 0 //no qswitch
            }

            laserDataFlow.value = newLaser
        }
    }

    fun selectedGraph() {
        viewModelScope.launch {
            var t = laserMedium?.host + ":" + laserMedium?.type
            val sh = if (shutter.value == 3) 1 else shutter.value
            val heh = if (isCylinder.value) 1 else 0

            var ou: OutputEntity

            Log.d("outputError", "host $t  shutter $sh  cylinder$heh")

            if (t == "Er:Yb:glass") {
                if (sh == 0) {
                    ou = outputRepository.getOutputData(
                        t, 1, 1,
                        1, 0
                    ).first()
                } else {
                    ou = outputRepository.getOutputData(
                        t, 1, 1,
                        1, 1
                    ).first()
                }
            } else {
                if (t != "Yb:YAG") {
                    t = "Nd:YAG"
                }

                if (pumpType.value == 1) {
                    ou = outputRepository.getOutputData(
                        t, 1, 1,
                        1, 0
                    ).first()
                } else {
                    ou = outputRepository.getOutputData(
                        t, heh, pumpScheme.value,
                        pumpType.value, sh
                    ).first()
                }


                if (ou == null || shutter.value == 2) {
                    ou = outputRepository.getOutputData(
                        t, 1, 1,
                        0, 2
                    ).first()
                }
            }

            val lo = laserOutputRepository.getLaserOutputData(ou.laserOutputId).first()
            val gp = giantPulseRepository.getGiantPulseData(ou.giantPulseId).firstOrNull()

            Log.d("outputError", ou.toString())

            output.value = ou
            laserOutput.value = lo
            giantPulse.value = gp
        }
    }

    fun save() {
        viewModelScope.launch {
            val saveAmplifier = amplifierRepository.insert(
                AmplifierEntity(
                    null,
                    amplifier!!.ampLength,
                    amplifier!!.ampPulseDuration,
                    amplifier!!.ampPulseEnergy,
                    amplifier!!.isMultipass,
                    waveform.value,
                    isAmplifier.value
                )
            )

            val saveConfiguration = configurationRepository.insert(
                ConfigurationEntity(
                    null,
                    isCylinder.value,
                    configuration!!.lc,
                    configuration!!.la,
                    configuration!!.ld,
                    configuration!!.lb,
                    configuration!!.roc,
                    configuration!!.ga,
                    configuration!!.gc,
                    configuration!!.hov,
                    configuration!!.dia
                )
            )
            val newLas = laserMedium!!.copy(laserMediumId = null)
            val saveLaserMedium = laserMediumRepository.insert(newLas)
            val savePump = pumpRepository.insert(
                PumpEntity(
                    null,
                    pump!!.tp,
                    pump!!.wp,
                    pump!!.hc,
                    pump!!.rp,
                    pump!!.lp,
                    pumpScheme.value,
                    pumpType.value,
                    pump!!.pformId,
                    pump!!.t1p,
                    pump!!.t2p,
                    pump!!.pformText
                )
            )
            val saveQSwitch = qSwitchRepository.insert(
                QSwitchEntity(
                    null,
                    isQSwitch.value,
                    isPQS.value,
                    qSwitch!!.lpsh,
                    qSwitch!!.npsh,
                    qSwitch!!.spt0,
                    qSwitch!!.sptd,
                    qSwitch!!.spt,
                    qSwitch!!.ssh,
                    qSwitch!!.fom,
                    isAQS.value,
                    qSwitch!!.lsh,
                    qSwitch!!.nsh,
                    qSwitch!!.st0,
                    qSwitch!!.stmax,
                    qSwitch!!.sts,
                    qSwitch!!.stf,
                    qSwitch!!.stoff,
                    qSwitch!!.AQStype,
                    qSwitch!!.sFrontType,
                    qSwitch!!.mode,
                    qSwitch!!.sf,
                    qSwitch!!.sk,
                    qSwitch!!.sb,
                    qSwitch!!.absCoef
                )
            )

            val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")

            val saveHost = SaveEntity(
                null,
                saveLaserMedium,
                savePump,
                saveQSwitch,
                saveConfiguration,
                saveAmplifier,
                laserMedium!!.host,
                laserMedium!!.type,
                LocalDateTime.now().format(formatter)
            )

            saveRepository.insert(saveHost)
        }
    }

    fun deleteSave(save: SaveEntity){
        viewModelScope.launch {
            saveRepository.delete(save)
        }
    }

    fun updateDataPumpWp(newText: String) {
        viewModelScope.launch {
            try {
                if (newText.isNotEmpty()) {
                    laserDataFlow.value.pump.wp = newText.toDouble()
                    laserDataFlow.value.timestamp = System.currentTimeMillis()
                    Log.d("model", laserDataFlow.value.pump.wp.toString())
                }
            } catch (e: Exception) {
                // Обработка исключения
                Log.e("YourTag", "An error occurred in updateDataPumpWp: ${e.message}")
            }
        }
    }

    private fun updateShutter() {
        shutter.value = when {
            isAQS.value && isPQS.value && isQSwitch.value -> 3
            isPQS.value && isQSwitch.value -> 2
            isAQS.value && isQSwitch.value -> 1
            else -> 0 //no qswitch
        }
    }

    fun updatePumpType(id: Int) {
        try {
            Log.d("laserH", id.toString())
            pumpType.value = id
        } catch (e: Exception) {
            // Обработка исключения
            Log.e("YourTag", "An error occurred in updateDataPumpWp: ${e.message}")
        }
    }

    fun updatePumpScheme(id: Int) {
        try {
            Log.d("laserH", id.toString())
            pumpScheme.value = id
        } catch (e: Exception) {
            // Обработка исключения
            Log.e("YourTag", "An error occurred in updateDataPumpWp: ${e.message}")
        }
    }

    fun updateIsCylinder(bool: Boolean) {
        try {
            isCylinder.value = bool
        } catch (e: Exception) {
            // Обработка исключения
            Log.e("YourTag", "An error occurred in updateDataPumpWp: ${e.message}")
        }
    }

    fun updateIsQSwitch(bool: Boolean) {
        try {
            isQSwitch.value = bool
            updateShutter()
        } catch (e: Exception) {
            // Обработка исключения
            Log.e("YourTag", "An error occurred in updateDataPumpWp: ${e.message}")
        }
    }

    fun updateIsAQS(bool: Boolean) {
        try {
            isAQS.value = bool
            updateShutter()
        } catch (e: Exception) {
            // Обработка исключения
            Log.e("YourTag", "An error occurred in updateDataPumpWp: ${e.message}")
        }
    }

    fun updateIsPQS(bool: Boolean) {
        try {
            isPQS.value = bool
            updateShutter()
        } catch (e: Exception) {
            // Обработка исключения
            Log.e("YourTag", "An error occurred in updateDataPumpWp: ${e.message}")
        }
    }

    fun updateFrontType(id: Int) {
        try {
            Log.d("laserH", id.toString())
            qSwitchFrontType.value = id
        } catch (e: Exception) {
            // Обработка исключения
            Log.e("YourTag", "An error occurred in updateDataPumpWp: ${e.message}")
        }
    }

    fun updateQSwitchType(id: Int) {
        try {
            Log.d("laserH", id.toString())
            qSwitchType.value = id
        } catch (e: Exception) {
            // Обработка исключения
            Log.e("YourTag", "An error occurred in updateDataPumpWp: ${e.message}")
        }
    }

    fun updateQSwitchMode(id: Int) {
        try {
            Log.d("laserH", id.toString())
            qSwitchMode.value = id
        } catch (e: Exception) {
            // Обработка исключения
            Log.e("YourTag", "An error occurred in updateDataPumpWp: ${e.message}")
        }
    }

    fun updateIsAmplifier(bool: Boolean) {
        try {
            isAmplifier.value = bool
        } catch (e: Exception) {
            // Обработка исключения
            Log.e("YourTag", "An error occurred in updateDataPumpWp: ${e.message}")
        }
    }

    fun updateWaveform(id: Int) {
        try {
            Log.d("laserH", id.toString())
            waveform.value = id
        } catch (e: Exception) {
            // Обработка исключения
            Log.e("YourTag", "An error occurred in updateDataPumpWp: ${e.message}")
        }
    }


    fun laserResultCalculations() {
        val laser = laserDataFlow.value
        val diffFunc = DiffFunc(laser)

        var diffResult = DiffResult()

        diffResult = diffFunc.Rk()

        Log.d("laser", diffResult.n.toString())
//        if (laser.amplifier.isUse == true) {
//            diffResult = diffFunc.RkAmp()
//            laser.ampDgauss = DoubleArray(laser.ampPulseTicks)
//            when (laser.amplifier.waveform) {
//                0 -> DiscreteGaussian(
//                    laser.ampDgauss,
//                    laser.amplifier.ampPulseDuration!!,
//                    laser.amplifier.ampPulseEnergy!!
//                )
//
//                1 -> DiscreteSinc(
//                    laser.ampDgauss,
//                    laser.amplifier.ampPulseDuration!!,
//                    laser.amplifier.ampPulseEnergy!!
//                )
//
//                else -> Log.d("err", "jeje")
//            }
//            val length = laser.ampInitialValues.size
//            val ampPulseTicks = laser.ampPulseTicks
//            val P = DoubleArray(ampPulseTicks)
//            System.arraycopy(laser.ampDgauss, 0, P, 0, ampPulseTicks)
//            this.PtoS(P, laser.e0ph, laser.iss, laser.ag)
//            val ampNumOfPasses = 1
//            val simTime: Double = when (laser.amplifier.waveform) {
//                0 -> laser.amplifier.ampPulseDuration!! * 0.001 * 3.0 / ampPulseTicks.toDouble()
//                1 -> laser.amplifier.ampPulseDuration!! * 0.001 * 2.258 / ampPulseTicks.toDouble()
//                else -> laser.amplifier.ampPulseDuration!! * 0.001 * 3.0 / ampPulseTicks.toDouble()
//            }
//
//            val numArray = Array(length) { DoubleArray(ampPulseTicks) }
//            laser.ampDgauss2 = Array(ampPulseTicks) { DoubleArray(ampNumOfPasses) }
//
//            for (index1 in 0 until ampNumOfPasses) {
//                for (index2 in 0 until laser.ampPulseTicks) {
//                    laser.ampInitialValues[0] = 0.0
//                    laser.ampInitialValues[4] = P[index2]
//                    diffResult = diffFunc.RkAmpNoRelaxation(
//                        laser.ampInitialValues,
//                        laser.laserMedium.ne!! * laser.configuration.la!! / 29979.2458
//                    )
//                    diffResult = diffFunc.RkAmpRelaxationOnly(laser.ampInitialValues, simTime)
//                    for (index3 in 0 until length) {
//                        numArray[index3][index2] = laser.ampInitialValues[index3]
//                    }
//                }
//
//                for (index4 in 0 until ampPulseTicks) {
//                    if (ampNumOfPasses > 1) {
//                        laser.ampDgauss2[index4][index1] =
//                            numArray[4][index4] * laser.ampPassT[index1]
//                        P[index4] = numArray[4][index4] * (1.0 - laser.ampPassT[index1])
//                    } else {
//                        laser.ampDgauss2[index4][index1] = numArray[4][index4]
//                    }
//                }
//
//                if ((laser.amplifier.ampLength!! + (laser.laserMedium.ne!! - 1.0) * laser.configuration.la!!) / 29979.2458 - simTime * laser.ampPulseTicks.toDouble() > 0.0) {
//                    diffResult = diffFunc.RkAmpRelaxationOnly(
//                        laser.ampInitialValues, (laser.amplifier.ampLength + (
//                                laser.laserMedium.ne - 1.0) * laser.configuration.la
//                                ) / 29979.2458 - simTime * laser.ampPulseTicks.toDouble()
//                    )
//                }
//            }
//        } else {
//            if (laser.optimization.isUse == false) {
//                diffResult = diffFunc.Rk()
//            } else {
//                // TODO: доделать optimization
//            }
//        }

    }

    //
//
//

//
//    fun hehe(){
//        Log.d("hehe", pumpData.value.toString())
//    }

    private fun gauss(x: Double, fwhh: Double, E0: Double, x0: Double): Double {
        val num = fwhh / (2.0 * sqrt(-2.0 * ln(0.5)))
        return E0 * 1.0 / (sqrt(2.0 * Math.PI) * num) * exp(-(x - x0) * (x - x0) / (2.0 * num * num))
    }

    private fun DiscreteGaussian(A: DoubleArray, ampPulseDuration: Double, ampPulseEnergy: Double) {
        val num1 = -ampPulseDuration * 1.5
        val num2 = ampPulseDuration * 1.5
        var x = num1
        for (index in A.indices) {
            A[index] = gauss(x, ampPulseDuration, ampPulseEnergy, 0.0)
            x += (num2 - num1) / (A.size - 1)
        }
    }

    private fun sinc(x: Double, fwhh: Double, E0: Double, x0: Double): Double {
        val num = 2.7831 / fwhh
        return num * E0 / 2.8363 * sin((x - x0) * num) * sin((x - x0) * num) / ((x - x0) * (x - x0) * num * num)
    }

    private fun DiscreteSinc(A: DoubleArray, ampPulseDuration: Double, ampPulseEnergy: Double) {
        val num1 = -1.0 * Math.PI * ampPulseDuration / 2.7831
        val num2 = (Math.PI * ampPulseDuration / 2.7831 - num1) / (A.size - 1)
        var x = num1
        for (index in A.indices) {
            A[index] = sinc(x, ampPulseDuration, ampPulseEnergy, 0.0)
            x += num2
        }
    }

    fun PtoS(P: DoubleArray, e0ph: Double, iss: Double, ag: Double) {
        for (index in P.indices) {
            P[index] = P[index] / (e0ph * iss * ag)
        }
    }

}


class MainViewModelFactory(
    private val amplifierRepository: AmplifierRepository,
    private val configurationRepository: ConfigurationRepository,
    private val hostRepository: HostRepository,
    private val laserMediumRepository: LaserMediumRepository,
    private val optimizationRepository: OptimizationRepository,
    private val pumpRepository: PumpRepository,
    private val qSwitchRepository: QSwitchRepository,
    private val saveRepository: SaveRepository,
    val outputRepository: OutputRepository,
    val laserOutputRepository: LaserOutputRepository,
    val giantPulseRepository: GiantPulseRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(
                amplifierRepository, configurationRepository,
                hostRepository, laserMediumRepository, optimizationRepository,
                pumpRepository, qSwitchRepository, saveRepository, outputRepository,
                laserOutputRepository, giantPulseRepository
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
