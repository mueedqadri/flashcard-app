package com.example.flashcards.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.flashcards.R
import com.example.flashcards.models.NoteModel

class NotesListRecyclerViewAdapter() : RecyclerView.Adapter<NotesListRecyclerViewAdapter.NoteListItem>() {
    private var notes = emptyList<NoteModel>()

    inner class NoteListItem(notesListItemView: View?) : RecyclerView.ViewHolder(notesListItemView!!) {
        val noteTitleTextView: TextView? = notesListItemView?.findViewById<TextView>(R.id.noteTitleTextView)
        val noteBodyTextView: TextView? = notesListItemView?.findViewById<TextView>(R.id.noteBodyTextView)
        var notePosition = 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteListItem {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        val noteListItemView: View = layoutInflater.inflate(R.layout.notes_list_item, parent, false)
        return NoteListItem(noteListItemView)
    }

    override fun onBindViewHolder(holder: NoteListItem, position: Int) {
        val note: NoteModel = notes[position]
        holder.noteTitleTextView?.text = note.noteTitle
        holder.noteBodyTextView?.text = note.noteBody
        holder.notePosition = position
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    fun setNotes(notes: List<NoteModel>) {
        this.notes = notes
        notifyDataSetChanged()
    }
}