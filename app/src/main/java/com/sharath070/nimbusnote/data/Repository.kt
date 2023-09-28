package com.sharath070.nimbusnote.data

import android.os.Looper
import android.util.Log
import androidx.lifecycle.LiveData

class Repository(private val dao: NotesDao) {

    fun getAllNotes(): LiveData<List<Notes>> {
        return dao.getNotes()
    }

    fun getHighPriorityNotes(): LiveData<List<Notes>> {
        return dao.getHighPriorityNotes()
    }

    fun getMediumPriorityNotes(): LiveData<List<Notes>> {
        return dao.getMediumPriorityNotes()
    }

    fun getLowPriorityNotes(): LiveData<List<Notes>> {
        return dao.getLowPriorityNotes()
    }


    fun insertNote(notes: Notes) {
        dao.insertNotes(notes)
        if (isMainThread()) {
            Log.d("execution-thread", "insertNote: Main Thread")
        } else {
            Log.d("execution-thread", "insertNote: bg Thread")
        }
    }

    fun deleteNotes(notes: Notes) {
        dao.deleteNotes(notes)
    }

    fun updateNotes(notes: Notes) {
        dao.updateNotes(notes)
    }
    private fun isMainThread(): Boolean {
        return Thread.currentThread() === Looper.getMainLooper().thread
    }

}