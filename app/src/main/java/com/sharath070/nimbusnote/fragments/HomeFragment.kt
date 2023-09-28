package com.sharath070.nimbusnote.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.sharath070.nimbusnote.R
import com.sharath070.nimbusnote.adapter.RecyclerViewAdapter
import com.sharath070.nimbusnote.adapter.SwipeToDelete
import com.sharath070.nimbusnote.data.Notes
import com.sharath070.nimbusnote.databinding.FragmentHomeBinding
import com.sharath070.nimbusnote.viewModel.NotesViewModel


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    private lateinit var viewModel: NotesViewModel
    private lateinit var adapter: RecyclerViewAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[NotesViewModel::class.java]


        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                notesFiltering(newText)
                return true
            }
        })


        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.rvDisplayNotes.layoutManager = layoutManager
        binding.rvDisplayNotes.setHasFixedSize(true)

        viewModel.getAllNotes.observe(viewLifecycleOwner) { notes ->
            adapter = RecyclerViewAdapter()
            adapter.setData(notes)
            binding.rvDisplayNotes.adapter = adapter

            swipeToDeleteFunction(notes)
        }

        binding.ivAllNotes.setOnClickListener {
            viewModel.getAllNotes.observe(viewLifecycleOwner) { notes ->
                adapter = RecyclerViewAdapter()
                adapter.setData(notes)
                binding.rvDisplayNotes.adapter = adapter

                swipeToDeleteFunction(notes)
                adapter.setData(notes)
                binding.rvDisplayNotes.adapter = adapter
            }
        }

        binding.tvHigh.setOnClickListener {
            viewModel.getHighPriorityNotes.observe(viewLifecycleOwner) { notes ->
                adapter = RecyclerViewAdapter()
                adapter.setData(notes)
                binding.rvDisplayNotes.adapter = adapter

                swipeToDeleteFunction(notes)
            }
        }
        binding.tvMedium.setOnClickListener {
            viewModel.getMediumPriorityNotes.observe(viewLifecycleOwner) { notes ->
                adapter = RecyclerViewAdapter()
                adapter.setData(notes)
                binding.rvDisplayNotes.adapter = adapter

                swipeToDeleteFunction(notes)
            }
        }
        binding.tvLow.setOnClickListener {
            viewModel.getLowPriorityNotes.observe(viewLifecycleOwner) { notes ->
                adapter = RecyclerViewAdapter()
                adapter.setData(notes)
                binding.rvDisplayNotes.adapter = adapter

                swipeToDeleteFunction(notes)
            }
        }




        binding.fabAddNote.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(R.id.action_homeFragment_to_createNoteFragment)
        }

    }

    private fun notesFiltering(newText: String?) {

    }

    private fun swipeToDeleteFunction(notes: List<Notes>) {
        val swipe = object : SwipeToDelete() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val note = adapter.noteList[position]

                val bottomSheet = BottomSheetDialog(requireContext())
                bottomSheet.setContentView(R.layout.dialog_delete)

                val tvYes = bottomSheet.findViewById<TextView>(R.id.dialogYes)!!
                val tvNo = bottomSheet.findViewById<TextView>(R.id.dialogNo)!!

                tvYes.setOnClickListener {
                    viewModel.delete(note)
                    bottomSheet.dismiss()
                }
                tvNo.setOnClickListener {
                    binding.rvDisplayNotes.layoutManager =
                        StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
                    binding.rvDisplayNotes.adapter = adapter
                    bottomSheet.dismiss()
                }

                bottomSheet.show()
                bottomSheet.setCancelable(false)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipe)
        itemTouchHelper.attachToRecyclerView(binding.rvDisplayNotes)
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}