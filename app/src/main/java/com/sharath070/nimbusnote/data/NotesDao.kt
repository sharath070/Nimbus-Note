package com.sharath070.nimbusnote.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert

@Dao
interface NotesDao {

    @Query("SELECT * FROM notes")
    fun getNotes(): LiveData<List<Notes>>

    @Upsert
    fun insertNotes(notes: Notes){
        Log.i("@@@@", Thread.currentThread().name)
    }

    @Delete
    fun deleteNotes(notes: Notes){
        Log.i("@@@@", Thread.currentThread().name)
    }

    @Update
    fun updateNotes(notes: Notes){
        Log.i("@@@@", Thread.currentThread().name)
    }

    @Query("SELECT * FROM notes WHERE priority = 3")
    fun getHighPriorityNotes(): LiveData<List<Notes>>

    @Query("SELECT * FROM notes WHERE priority = 2")
    fun getMediumPriorityNotes(): LiveData<List<Notes>>

    @Query("SELECT * FROM notes WHERE priority = 1")
    fun getLowPriorityNotes(): LiveData<List<Notes>>

}