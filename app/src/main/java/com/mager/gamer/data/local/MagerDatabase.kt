package com.mager.gamer.data.local

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

abstract class MagerDatabase: RoomDatabase() {

    companion object {
        private var database: MagerDatabase? = null

        fun instance(context: Context): MagerDatabase {
            if (database == null) {
                synchronized(MagerDatabase::class) {
                    database = Room.databaseBuilder(
                        context.applicationContext,
                        MagerDatabase::class.java,
                        "mager_database.db"
                    ).build()
                }
            }
            return database!!
        }

        fun destroyInstance() {
            database = null
        }
    }

}