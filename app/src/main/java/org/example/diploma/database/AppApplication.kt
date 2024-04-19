package org.example.diploma.database

import android.app.Application
import org.example.diploma.database.amplifier.AmplifierRepository
import org.example.diploma.database.configuration.ConfigurationRepository
import org.example.diploma.database.host.HostRepository
import org.example.diploma.database.laserMedium.LaserMediumRepository
import org.example.diploma.database.optimization.OptimizationRepository
import org.example.diploma.database.pump.PumpRepository
import org.example.diploma.database.qSwitch.QSwitchRepository
import org.example.diploma.database.save.SaveRepository

class AppApplication: Application() {
    val database by lazy { AppDatabase.getDatabase(this) }

    val hostRepository by lazy { HostRepository(database.hostDao()) }

    val configurationRepository by lazy { ConfigurationRepository(database.configurationDao()) }

    val laserMediumRepository by lazy { LaserMediumRepository(database.laserMediumDao()) }

    val pumpRepository by lazy { PumpRepository(database.pumpDao()) }

    val qSwitchRepository by lazy { QSwitchRepository(database.qSwitchDao()) }

    val saveRepository by lazy { SaveRepository(database.saveDao()) }

    val amplifierRepository by lazy { AmplifierRepository(database.amplifierDao()) }

    val optimizationRepository by lazy { OptimizationRepository(database.optimizationDao()) }
}

//    override fun onCreate() {
//        super.onCreate()
//        startKoin {
//            androidContext(this@AppApplication)
//            modules(listOf(appModule))
//        }
//    }
//
//    private val appModule = module {
//        single { database.amplifierDao() }
//        single { database.configurationDao() }
//        single { database.hostDao() }
//        single { database.laserMediumDao() }
//        single { database.optimizationDao() }
//        single { database.pumpDao() }
//        single { database.qSwitchDao() }
//        single { database.saveDao() }
//
//        single { AmplifierRepository(get()) }
//        single { ConfigurationRepository(get()) }
//        single { HostRepository(get()) }
//        single { LaserMediumRepository(get()) }
//        single { OptimizationRepository(get()) }
//        single { PumpRepository(get()) }
//        single { QSwitchRepository(get()) }
//        single { SaveRepository(get()) }
//
//
//        single { MainViewModel(get(), get(), get(), get(), get(), get(), get(), get()) }
//    }
