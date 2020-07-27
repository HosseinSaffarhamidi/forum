package ir.adromsh.forum.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(RCourse::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun courseDao():CourseDao
    companion object {
        private var instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            if (instance != null) {
                return instance!!
            } else {
                instance = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "cl_database"
                ).allowMainThreadQueries().build()

                return instance!!
            }
        }
    }
}