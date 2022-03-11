package com.example.flashcardapp.persistance

import com.example.flashcardapp.models.BasicFlashCardModel

object BasicFlashcardPersistance {
    val notes = ArrayList<BasicFlashCardModel>()

    init {
        initializeNotesPersistence();
    }

    private fun initializeNotesPersistence() {

            val flashcardTitle:String = ""
            val flashcardBody: String = ""
            val note = BasicFlashCardModel(flashcardTitle, flashcardBody)
            notes.add(note)

        println(notes.size)
    }
}