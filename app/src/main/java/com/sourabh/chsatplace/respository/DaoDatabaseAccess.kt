package com.sourabh.chsatplace.respository

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [(ChatEntityModel::class),(PresenceEntityModel::class)], version = 2, exportSchema = false)
abstract class DaoDatabaseAccess : RoomDatabase() {
    abstract fun ChattingDao(): ChattingDao
    abstract fun presenceDao():PresenceDao

    //    abstract fun ChattViewDao():ChattViewDao
    companion object {
        @Volatile
        private var instance: DaoDatabaseAccess? = null


        operator fun invoke(context: Context): DaoDatabaseAccess {
            val tempInstance = instance
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val inst = Room.databaseBuilder(
                    context.applicationContext,
                    DaoDatabaseAccess::class.java,
                    "chatDb"
                ).build()
                instance = inst
                return inst
            }
        }
    }

}