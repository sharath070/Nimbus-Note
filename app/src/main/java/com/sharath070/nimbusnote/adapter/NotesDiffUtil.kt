package com.sharath070.nimbusnote.adapter

import androidx.recyclerview.widget.DiffUtil
import com.sharath070.nimbusnote.data.Notes

class NotesDiffUtil(
    private val oldList: List<Notes>,
    private val newList: List<Notes>
) : DiffUtil.Callback(){

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] === newList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
                && oldList[oldItemPosition].title == newList[newItemPosition].title
                && oldList[oldItemPosition].subTitle == newList[newItemPosition].subTitle
                && oldList[oldItemPosition].notes == newList[newItemPosition].notes
                && oldList[oldItemPosition].date == newList[newItemPosition].date
                && oldList[oldItemPosition].priority == newList[newItemPosition].priority
    }

}