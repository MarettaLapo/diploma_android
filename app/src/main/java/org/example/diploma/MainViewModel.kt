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

    lateinit var laserMediumData : LiveData<LaserMediumEntity>
    lateinit var configurationData : LiveData<ConfigurationEntity>
    lateinit var pumpData : LiveData<PumpEntity>
    lateinit var qSwitchData : LiveData<QSwitchEntity>
    lateinit var amplifierData : LiveData<AmplifierEntity>
    lateinit var optimizationData : LiveData<OptimizationEntity>
    fun selectedHost(host: HostEntity){
        laserMediumData = laserMediumRepository.getLaserMediumData(host.laserMediumId)

        configurationData = configurationRepository.getConfigurationData(host.configurationId)

        pumpData = pumpRepository.getPumpData(host.pumpId)

        qSwitchData = qSwitchRepository.getQSwitchData(host.qSwitchId)

        amplifierData = amplifierRepository.getAmplifierData(host.amplifierId)

        optimizationData = optimizationRepository.getOptimizationData(host.optimizationId)

    }

    private val dataFlow1: MutableStateFlow<Hehe> = MutableStateFlow(Hehe("alice", 21))

    private val dataFlow2: MutableStateFlow<Hehe> = MutableStateFlow(Hehe("sasha", 22))

    private val combinedDataFlow: MutableStateFlow<CombinedData> =
        MutableStateFlow(CombinedData(Hehe("he", 1), Hehe("he", 2), ))

    init {
        // Комбинируем данные из двух репозиториев и обновляем combinedDataFlow
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

    fun getCombinedDataFlow(): StateFlow<CombinedData> {
        return combinedDataFlow
    }

    lateinit var diffResult: DiffResult
    fun laserResultCalculations(){
        val laser = Laser()
        val diffFunc = DiffFunc(laser)

        if(laser.amplifier.isUse == true){
            var diffResult = diffFunc.RkAmp(laser)
        }
        else{
            if(laser.optimization.isUse == false){
                var diffResult =  diffFunc.Rk()
            }
        }
        diffResult = DiffResult()
    }



    val allHosts = hostRepository.getAllHosts()

    fun hehe(){
        Log.d("hehe", pumpData.value.toString())
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
