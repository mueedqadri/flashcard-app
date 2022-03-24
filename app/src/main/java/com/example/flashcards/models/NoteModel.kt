package com.example.flashcards.models

data class NoteModel(var noteTitle: String, var noteBody: String) {
    override fun toString(): String {
        // Return attribute changed from noteBody to noteTitle
        // as we should display note title instead of body in listview
        // If title is blank then only we will display note body in listview
        if (noteTitle.isNotBlank()) {
            return noteTitle
        }
        return noteBody
    }
}