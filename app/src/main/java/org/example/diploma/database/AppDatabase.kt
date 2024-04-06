package org.example.diploma.database

import android.content.Context
import android.util.Log
import androidx.compose.runtime.rememberCoroutineScope
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.example.diploma.database.entities.AmplifierEntity
import org.example.diploma.database.entities.ConfigurationEntity
import org.example.diploma.database.entities.HostEntity
import org.example.diploma.database.entities.LaserMediumEntity
import org.example.diploma.database.entities.OptimizationEntity
import org.example.diploma.database.entities.PumpEntity
import org.example.diploma.database.entities.QSwitchEntity
import org.example.diploma.database.entities.SaveEntity
import java.util.concurrent.Executors

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
                val scope = CoroutineScope(Dispatchers.IO)
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(AppDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }

        private class AppDatabaseCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { /*database ->*/
                    scope.launch {
                        INSTANCE?.getPumpDao()?.insertPump(PumpEntity(
                            0L,
                            1.0,
                            1.0,
                            1.0,
                            1.0,
                            1.0,
                            "Shoto",
                            "Gdeto",
                            "Hehe",
                            1.0,
                            1.0,
                        ))
                    }
                }

                INSTANCE?.let { /*database ->*/
                    val data = scope.async {
                        INSTANCE?.getPumpDao()?.getAllPumps()
                    }
                    scope.launch {
                        for(item in data.await()!!){
                            Log.d("asdsa", item.toString())
                        }
                    }
                }
            }
        }
    }
}