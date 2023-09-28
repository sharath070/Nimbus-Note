package com.sharath070.nimbusnote.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Notes::class], version = 1)
abstract class NotesDatabase: RoomDatabase() {

    abstract fun notesDao() : NotesDao

    companion object{
        @Volatile
        var INSTANCE: NotesDatabase? = null

        fun getDatabase(context: Context): NotesDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NotesDatabase::class.java,
                    "item_database"
                ).build()

                INSTANCE = instance
                return instance
            }
        }
    }

}