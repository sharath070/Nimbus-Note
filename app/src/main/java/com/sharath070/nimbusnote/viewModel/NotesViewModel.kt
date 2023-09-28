package com.sharath070.nimbusnote.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.sharath070.nimbusnote.data.Notes
import com.sharath070.nimbusnote.data.NotesDatabase
import com.sharath070.nimbusnote.data.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotesViewModel(application: Application) : AndroidViewModel(application) {

    private val dao = NotesDatabase.getDatabase(application).notesDao()
    private val repository: Repository = Repository(dao)

    val  getAllNotes: LiveData<List<Notes>> = repository.getAllNotes()
    val  getHighPriorityNotes: LiveData<List<Notes>> = repository.getHighPriorityNotes()
    val  getMediumPriorityNotes: LiveData<List<Notes>> = repository.getMediumPriorityNotes()
    val  getLowPriorityNotes: LiveData<List<Notes>> = repository.getLowPriorityNotes()

    fun insert(notes: Notes){
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertNote(notes)
        }
    }


    fun update(notes: Notes){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateNotes(notes)
        }
    }

    fun delete(notes: Notes){
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteNotes(notes)
        }
    }
}