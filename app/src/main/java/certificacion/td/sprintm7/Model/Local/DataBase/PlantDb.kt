package certificacion.td.sprintm7.Model.Local.DataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import certificacion.td.sprintm7.Model.Local.Entities.PlantByIdEntity
import certificacion.td.sprintm7.Model.Local.Entities.PlantEntity
import certificacion.td.sprintm7.Model.Local.PlantDao

@Database(
    entities = [PlantEntity::class, PlantByIdEntity::class],
    version = 1,
    exportSchema = false
)
abstract class PlantDb : RoomDatabase() {

    abstract fun getPlantDao(): PlantDao

    companion object {

        @Volatile
        private var
                INSTANCE: PlantDb? = null

        fun getDataBase(context: Context): PlantDb {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PlantDb::class.java, "plantDb"
                )
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}