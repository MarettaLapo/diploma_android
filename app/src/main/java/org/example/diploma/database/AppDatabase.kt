package org.example.diploma.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import org.example.diploma.database.entities.AmplifierEntity
import org.example.diploma.database.entities.ConfigurationEntity
import org.example.diploma.database.entities.HostEntity
import org.example.diploma.database.entities.LaserMediumEntity
import org.example.diploma.database.entities.OptimizationEntity
import org.example.diploma.database.entities.PumpEntity
import org.example.diploma.database.entities.QSwitchEntity
import org.example.diploma.database.entities.SaveEntity

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
//    abstract fun getHostDao(): HostEntity.HostDao
//
//    abstract fun getSaveDao(): SaveEntity.SaveDao
//
//    abstract fun getQSwitchDao(): QSwitchEntity.QSwitchDao

    abstract fun getPumpDao(): PumpEntity.PumpDao

//    abstract fun getOptimizationDao(): OptimizationEntity.OptimizationDao
//
//    abstract fun getLaserMediumDao(): LaserMediumEntity.LaserMediumDao
//
//    abstract fun getConfigurationDao(): ConfigurationEntity.ConfigurationDao
//
//    abstract fun getAmplifierDao(): AmplifierEntity.AmplifierDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            // Populate the database with initial data
                            // For example:
                            val pumpDao = INSTANCE?.getPumpDao()
                            pumpDao?.insertPump(PumpEntity())
                            pumpDao?.insertPump(PumpEntity())
                        }
                    })
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }

        private class AppDatabaseCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {
            /**
             * Override the onCreate method to populate the database.
             */
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)

            }
        }
    }
}