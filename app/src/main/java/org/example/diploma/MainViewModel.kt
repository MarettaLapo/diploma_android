package org.example.diploma

import android.util.Log
import androidx.databinding.Bindable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import org.example.diploma.database.CombinedData
import org.example.diploma.database.Hehe
import org.example.diploma.database.amplifier.AmplifierEntity
import org.example.diploma.database.amplifier.AmplifierRepository
import org.example.diploma.database.configuration.ConfigurationEntity
import org.example.diploma.database.configuration.ConfigurationRepository
import org.example.diploma.database.host.HostEntity
import org.example.diploma.database.host.HostRepository
import org.example.diploma.database.laserMedium.LaserMediumEntity
import org.example.diploma.database.laserMedium.LaserMediumRepository
import org.example.diploma.database.optimization.OptimizationEntity
import org.example.diploma.database.optimization.OptimizationRepository
import org.example.diploma.database.pump.PumpEntity
import org.example.diploma.database.pump.PumpRepository
import org.example.diploma.database.qSwitch.QSwitchEntity
import org.example.diploma.database.qSwitch.QSwitchRepository
import org.example.diploma.database.save.SaveRepository
import org.example.diploma.laser.DiffFunc
import org.example.diploma.laser.DiffResult
import org.example.diploma.laser.Laser
import kotlin.math.exp
import kotlin.math.ln
import kotlin.math.sqrt

class MainViewModel(
    val amplifierRepository: AmplifierRepository,
    val configurationRepository: ConfigurationRepository,
    val hostRepository: HostRepository,
    val laserMediumRepository: LaserMediumRepository,
    val optimizationRepository: OptimizationRepository,
    val pumpRepository: PumpRepository,
    val qSwitchRepository: QSwitchRepository,
    val saveRepository: SaveRepository
) : ViewModel() {

    lateinit var laserMediumData: LiveData<LaserMediumEntity>
    lateinit var configurationData: LiveData<ConfigurationEntity>
    lateinit var pumpData: LiveData<PumpEntity>
    lateinit var qSwitchData: LiveData<QSwitchEntity>
    lateinit var amplifierData: LiveData<AmplifierEntity>
    lateinit var optimizationData: LiveData<OptimizationEntity>
    fun selectedHost(host: HostEntity) {
        laserMediumData = laserMediumRepository.getLaserMediumData(host.laserMediumId)

        configurationData = configurationRepository.getConfigurationData(host.configurationId)

        pumpData = pumpRepository.getPumpData(host.pumpId)

        qSwitchData = qSwitchRepository.getQSwitchData(host.qSwitchId)

        amplifierData = amplifierRepository.getAmplifierData(host.amplifierId)

        optimizationData = optimizationRepository.getOptimizationData(host.optimizationId)

    }

    lateinit var  laserDataFlow: MutableStateFlow<Laser>

    fun h(host: HostEntity){

        viewModelScope.launch {
            combine(
                dataFlow1,
                dataFlow2
            ) { ag, ap ->
                // Вычисляем ah = ag + ap
                CombinedData(ag, ap)
            }.collect { combinedData ->
                combinedDataFlow.value = combinedData
            }
        }
    }

    //    init {
//        // Комбинируем данные из двух репозиториев и обновляем combinedDataFlow
//        viewModelScope.launch {
//            combine(
//                dataFlow1,
//                dataFlow2
//            ) { ag, ap ->
//                // Вычисляем ah = ag + ap
//                CombinedData(ag, ap)
//            }.collect { combinedData ->
//                combinedDataFlow.value = combinedData
//            }
//        }
//    }
//
//    fun getCombinedDataFlow(): StateFlow<CombinedData> {
//        return combinedDataFlow
//    }
//


    val allHosts = hostRepository.getAllHosts()

    fun laserResultCalculations() {
        val laser = laserDataFlow.value
        val diffFunc = DiffFunc(laser)

        var diffResult = DiffResult()

        if (laser.amplifier.isUse == true) {
            diffResult = diffFunc.RkAmp()
            laser.ampDgauss = DoubleArray(laser.ampPulseTicks)
            when (laser.amplifier.waveform) {
                0 -> DiscreteGaussian(laser.ampDgauss)
                1 -> DiscreteSinc(laser.ampDgauss)
                else -> Log.d("err", "jeje")
            }
            val length = laser.ampInitialValues.size
            val ampPulseTicks = laser.ampPulseTicks
            val P = DoubleArray(ampPulseTicks)
            System.arraycopy(laser.ampDgauss, 0, P, 0, ampPulseTicks)
            this.PtoS(P)
            val ampNumOfPasses = 1
            val simTime: Double = when (laser.amplifier.waveform) {
                0 ->laser.amplifier.ampPulseDuration!! * 0.001 * 3.0 / ampPulseTicks.toDouble()
                1 -> laser.amplifier.ampPulseDuration!! * 0.001 * 2.258 / ampPulseTicks.toDouble()
                else -> laser.amplifier.ampPulseDuration!! * 0.001 * 3.0 / ampPulseTicks.toDouble()
            }

            val numArray = Array(length) { DoubleArray(ampPulseTicks) }
            laser.ampDgauss2 = Array(ampPulseTicks) { DoubleArray(ampNumOfPasses) }

            for (index1 in 0 until ampNumOfPasses) {
                for (index2 in 0 until laser.ampPulseTicks) {
                    laser.ampInitialValues[0] = 0.0
                    laser.ampInitialValues[4] = P[index2]
                    diffResult = diffFunc.RkAmpNoRelaxation(laser.ampInitialValues, laser.laserMedium.ne!! * laser.configuration.la!! / 29979.2458)
                    diffResult = diffFunc.RkAmpRelaxationOnly(laser.ampInitialValues, simTime)
                    for (index3 in 0 until length) {
                        numArray[index3][index2] = laser.ampInitialValues[index3]
                    }
                }

                for (index4 in 0 until ampPulseTicks) {
                    if (ampNumOfPasses > 1) {
                        laser.ampDgauss2[index4][index1] = numArray[4][index4] * laser.ampPassT[index1]
                        P[index4] = numArray[4][index4] * (1.0 - laser.ampPassT[index1])
                    } else {
                        laser.ampDgauss2[index4][index1] = numArray[4][index4]
                    }
                }

                if ((laser.amplifier.ampLength!! + (laser.laserMedium.ne!! - 1.0) * laser.configuration.la!!) / 29979.2458 - simTime * laser.ampPulseTicks.toDouble() > 0.0) {
                    diffResult = diffFunc.RkAmpRelaxationOnly(laser.ampInitialValues, (laser.amplifier.ampLength!! + (
                            laser.laserMedium.ne!! - 1.0) * laser.configuration.la!!
                            ) / 29979.2458 - simTime * laser.ampPulseTicks.toDouble())
                }
            }
        } else {
            if (laser.optimization.isUse == false) {
                diffResult = diffFunc.Rk()
            } else {
                // TODO: доделать optimization
            }
        }
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
    private fun DiscreteGaussian(A: DoubleArray) {
        val num1 = -laser.amplifier.ampPulseDuration!! * 1.5
        val num2 = laser.amplifier.ampPulseDuration!! * 1.5
        var x = num1
        for (index in A.indices) {
            A[index] = gauss(x, laser.amplifier.ampPulseDuration!!, laser.amplifier.ampPulseEnergy!!, 0.0)
            x += (num2 - num1) / (A.size - 1)
        }
    }

    private fun sinc(x: Double, fwhh: Double, E0: Double, x0: Double): Double {
        val num = 2.7831 / fwhh
        return num * E0 / 2.8363 * Math.sin((x - x0) * num) * Math.sin((x - x0) * num) / ((x - x0) * (x - x0) * num * num)
    }

    private fun DiscreteSinc(A: DoubleArray) {
        val num1 = -1.0 * Math.PI * laser.amplifier.ampPulseDuration!! / 2.7831
        val num2 = (Math.PI * laser.amplifier.ampPulseDuration!! / 2.7831 - num1) / (A.size - 1)
        var x = num1
        for (index in A.indices) {
            A[index] = sinc(x, laser.amplifier.ampPulseDuration!!, laser.amplifier.ampPulseEnergy!!, 0.0)
            x += num2
        }
    }

    fun PtoS(P: DoubleArray){
        for (index in P.indices) {
            P[index] = P[index] / (laser.e0ph * laser.iss * laser.ag)
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
    private val saveRepository: SaveRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(
                amplifierRepository, configurationRepository,
                hostRepository, laserMediumRepository, optimizationRepository,
                pumpRepository, qSwitchRepository, saveRepository
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
