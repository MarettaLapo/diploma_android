package org.example.diploma.database

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import org.example.diploma.database.amplifier.AmplifierRepository
import org.example.diploma.database.host.HostRepository
import org.example.diploma.database.optimization.OptimizationRepository
import org.example.diploma.database.pump.PumpRepository
import org.example.diploma.database.save.SaveRepository

class AppApplication: Application() {
    // No need to cancel this scope as it'll be torn down with the process

    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    val database by lazy { AppDatabase.getDatabase(this) }

    val hostRepository by lazy { HostRepository(database.hostDao()) }

    val saveRepository by lazy { SaveRepository(database.saveDao()) }

    val amplifierRepository by lazy { AmplifierRepository(database.amplifierDao()) }

    val optimizationRepository by lazy { OptimizationRepository(database.optimizationDao()) }
}
