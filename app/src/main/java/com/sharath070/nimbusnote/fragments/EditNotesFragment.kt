package com.sharath070.nimbusnote.fragments

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.sharath070.nimbusnote.R
import com.sharath070.nimbusnote.data.Notes
import com.sharath070.nimbusnote.databinding.FragmentEditNotesBinding
import com.sharath070.nimbusnote.viewModel.NotesViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class EditNotesFragment : Fragment() {

    private var _binding: FragmentEditNotesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditNotesBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


    val prevNote by navArgs<EditNotesFragmentArgs>()
    private lateinit var viewModel: NotesViewModel

    private var priority: String = "1"


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.etTitle.setText(prevNote.note.title)
        binding.etSubtitle.setText(prevNote.note.subTitle)
        binding.etNote.setText(prevNote.note.notes)

        viewModel = ViewModelProvider(this)[NotesViewModel::class.java]

        when (prevNote.note.priority) {
            "1" -> {
                priority = "1"
                binding.pGreen.setImageResource(R.drawable.ic_save)
                binding.pRed.setImageResource(0)
                binding.pYellow.setImageResource(0)
            }
            "2" -> {
                priority = "2"
                binding.pYellow.setImageResource(R.drawable.ic_save)
                binding.pRed.setImageResource(0)
                binding.pGreen.setImageResource(0)
            }
            "3" -> {
                priority = "3"
                binding.pRed.setImageResource(R.drawable.ic_save)
                binding.pYellow.setImageResource(0)
                binding.pGreen.setImageResource(0)
            }
        }
        binding.pGreen.setOnClickListener {
            priority = "1"
            binding.pGreen.setImageResource(R.drawable.ic_save)
            binding.pRed.setImageResource(0)
            binding.pYellow.setImageResource(0)
        }

        binding.pYellow.setOnClickListener {
            priority = "2"
            binding.pYellow.setImageResource(R.drawable.ic_save)
            binding.pRed.setImageResource(0)
            binding.pGreen.setImageResource(0)
        }

        binding.pRed.setOnClickListener {
            priority = "3"
            binding.pRed.setImageResource(R.drawable.ic_save)
            binding.pYellow.setImageResource(0)
            binding.pGreen.setImageResource(0)
        }

        binding.fabSaveNotes.setOnClickListener {
            updateNotes()
        }

    }

    private fun updateNotes() {
        val title = binding.etTitle.text.toString()
        val subTitle = binding.etSubtitle.text.toString()
        val notes = binding.etNote.text.toString()

        val currentDateAndTime = Date()
        val formattedDateAndTime = formatDateAndTime(currentDateAndTime)

        val note = Notes(prevNote.note.id, title, subTitle, notes, formattedDateAndTime, priority)
        if (inputCheck(title, notes)){
            viewModel.update(note)
            Toast.makeText(requireContext(), "Note updated successfully!", Toast.LENGTH_SHORT)
                .show()
            findNavController().navigate(R.id.action_editNotesFragment_to_homeFragment)
        }
        else{
            Toast.makeText(requireContext(), "Title and note must bi filled.", Toast.LENGTH_SHORT)
                .show()
        }



    }

    private fun formatDateAndTime(date: Date): String {
        val dateFormat = SimpleDateFormat("dd MMMM yyyy hh:mm a", Locale.getDefault())
        return dateFormat.format(date)
    }

    private fun inputCheck(title: String, note: String): Boolean {
        return !(TextUtils.isEmpty(title) || TextUtils.isEmpty(note))
    }



}