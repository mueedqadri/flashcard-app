package com.example.flashcards.models

data class FolderModel(var folderName: String) {
    override fun toString(): String {
        return folderName
    }
}