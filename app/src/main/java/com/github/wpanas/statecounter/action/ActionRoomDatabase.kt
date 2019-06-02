package com.github.wpanas.statecounter.action

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.github.wpanas.statecounter.infra.converters.InstantConverter
import com.github.wpanas.statecounter.infra.converters.UuidConverter

@Database(entities = [Action::class], version = 2)
@TypeConverters(value = [UuidConverter::class, InstantConverter::class, ActionTypeConverter::class])
abstract class ActionRoomDatabase : RoomDatabase() {
    abstract fun actionDao(): ActionDao

    companion object {
        @Volatile
        private var INSTANCE: ActionRoomDatabase? = null

        const val DATABASE_NAME = "Action_database"

        fun getDatabase(context: Context): ActionRoomDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ActionRoomDatabase::class.java,
                    DATABASE_NAME
                )
                    .fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance

                return instance
            }
        }
    }
}