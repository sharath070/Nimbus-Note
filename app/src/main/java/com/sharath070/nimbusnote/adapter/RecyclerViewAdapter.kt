package com.sharath070.nimbusnote.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.sharath070.nimbusnote.R
import com.sharath070.nimbusnote.data.Notes
import com.sharath070.nimbusnote.databinding.NoteItemBinding
import com.sharath070.nimbusnote.fragments.HomeFragmentDirections

class RecyclerViewAdapter :
    RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    var noteList = emptyList<Notes>()

    private val staggeredLayoutManager: StaggeredGridLayoutManager by lazy {
        StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerViewAdapter.ViewHolder {
        val binding = NoteItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerViewAdapter.ViewHolder, position: Int) {


        holder.itemBinding.apply {
            tvTitle.text = noteList[position].title
            tvNote.text = "${noteList[position].subTitle}\n${noteList[position].notes}"

            when (noteList[position].priority) {
                "1" -> priority.setBackgroundResource(R.drawable.green_dot)
                "2" -> priority.setBackgroundResource(R.drawable.yellow_bg)
                "3" -> priority.setBackgroundResource(R.drawable.red_dot)
            }
            tvDateTime.text = noteList[position].date
            tvDateTime.isSelected = true
        }


        holder.itemBinding.root.setOnClickListener {
            val action =
                HomeFragmentDirections.actionHomeFragmentToEditNotesFragment(noteList[position])

            Navigation.findNavController(it).navigate(action)
        }


    }

    fun setData(givenList: List<Notes>) {
        this.noteList = givenList
        notifyDataSetChanged()
    }


    override fun getItemCount(): Int {
        return noteList.size
    }




    inner class ViewHolder(val itemBinding: NoteItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

    }
}