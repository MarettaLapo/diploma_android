package org.example.diploma.database

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.example.diploma.database.amplifier.AmplifierDao
import org.example.diploma.database.amplifier.AmplifierEntity
import org.example.diploma.database.configuration.ConfigurationDao
import org.example.diploma.database.configuration.ConfigurationEntity
import org.example.diploma.database.host.HostDao
import org.example.diploma.database.host.HostEntity
import org.example.diploma.database.laserMedium.LaserMediumDao
import org.example.diploma.database.laserMedium.LaserMediumEntity
import org.example.diploma.database.optimization.OptimizationDao
import org.example.diploma.database.optimization.OptimizationEntity
import org.example.diploma.database.pump.PumpDao
import org.example.diploma.database.pump.PumpEntity
import org.example.diploma.database.qSwitch.QSwitchDao
import org.example.diploma.database.qSwitch.QSwitchEntity
import org.example.diploma.database.save.SaveDao
import org.example.diploma.database.save.SaveEntity

@Database(
    version = 1,
    entities = [
        AmplifierEntity::class,
        ConfigurationEntity::class,
        HostEntity::class,
        LaserMediumEntity::class,
        OptimizationEntity::class,
        PumpEntity::class,
        QSwitchEntity::class,
        SaveEntity::class,
    ]
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun amplifierDao(): AmplifierDao

    abstract fun configurationDao(): ConfigurationDao

    abstract fun hostDao(): HostDao

    abstract fun laserMediumDao(): LaserMediumDao

    abstract fun optimizationDao(): OptimizationDao

    abstract fun pumpDao(): PumpDao

    abstract fun qSwitchDao(): QSwitchDao

    abstract fun saveDao(): SaveDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "12"
                ).createFromAsset("app_database.db")
                    .build()
                INSTANCE = instance
                Log.d("cat123", "tata")

                // return instance
                instance
            }

        }
    }
}