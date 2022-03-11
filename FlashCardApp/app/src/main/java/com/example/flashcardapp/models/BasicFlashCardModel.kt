package com.example.flashcardapp.models

data class BasicFlashCardModel(var flashcardTitle: String, var flashcardBody: String) {
    override fun toString(): String {
        // Return attribute changed from noteBody to noteTitle
        // as we should display note title instead of body in listview
        // If title is blank then only we will display note body in listview
        if (flashcardTitle.isNotBlank()) {
            return flashcardTitle
        }
        return flashcardBody
    }
}