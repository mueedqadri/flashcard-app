package com.example.flashcards.models

import java.io.Serializable

data class FlashCardModel(var id: String, var question: String, var answer: String, var folderId: Int) : Serializable