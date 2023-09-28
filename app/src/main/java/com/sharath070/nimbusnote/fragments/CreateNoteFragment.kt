package com.sharath070.nimbusnote.fragments

import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Note
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.sharath070.nimbusnote.R
import com.sharath070.nimbusnote.data.Notes
import com.sharath070.nimbusnote.databinding.FragmentCreateNoteBinding
import com.sharath070.nimbusnote.viewModel.NotesViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class CreateNoteFragment : Fragment() {

    private var _binding: FragmentCreateNoteBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateNoteBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    private lateinit var viewModel: NotesViewModel

    private var priority: String = "1"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[NotesViewModel::class.java]

        binding.pGreen.setImageResource(R.drawable.ic_save)

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
            createNotes()

        }


    }


    private fun createNotes() {
        val title = binding.etTitle.text.toString()
        val subTitle = binding.etSubtitle.text.toString()
        val notes = binding.etNote.text.toString()

        val currentDateAndTime = Date()
        val formattedDateAndTime = formatDateAndTime(currentDateAndTime)

        val note = Notes(null, title, subTitle, notes, formattedDateAndTime, priority)
        if (inputCheck(title, notes)) {
            viewModel.insert(note)
            Toast.makeText(requireContext(), "Note added successfully!", Toast.LENGTH_SHORT)
                .show()
            findNavController().navigate(R.id.action_createNoteFragment_to_homeFragment)
        } else {
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