package com.example.flashcards.persistence

import com.example.flashcards.models.NoteModel

object NotesPersistence {
    val notes = ArrayList<NoteModel>()

    init {
        initializeNotesPersistence();
    }

    private fun initializeNotesPersistence() {
        for (i in 1..4) {
            val noteTitle = "Note $i"
            val noteBody = "I am note $i"
            val note = NoteModel(noteTitle, noteBody)
            notes.add(note)
        }
        println(notes.size)
    }
}