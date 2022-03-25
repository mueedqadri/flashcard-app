package com.example.flashcards.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.flashcards.R
import com.example.flashcards.models.FlashCardModel

class NotesListRecyclerViewAdapter() : RecyclerView.Adapter<NotesListRecyclerViewAdapter.NoteListItem>() {
    private var notes = emptyList<FlashCardModel>()

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
        val flashCard: FlashCardModel = notes[position]
        holder.noteTitleTextView?.text = flashCard.question
        holder.noteBodyTextView?.text = flashCard.answer
        holder.notePosition = position
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    fun setNotes(flashCards: List<FlashCardModel>) {
        this.notes = flashCards
        notifyDataSetChanged()
    }
}