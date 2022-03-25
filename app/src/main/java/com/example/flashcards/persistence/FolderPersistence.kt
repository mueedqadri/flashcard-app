package com.example.flashcards.persistence

import com.example.flashcards.models.FolderModel
import com.example.flashcards.models.NoteModel

object FolderPersistence {
    val folderList = ArrayList<FolderModel>()

    init {
        initializeFolderListPersistence();
    }

    private fun initializeFolderListPersistence() {
        for (i in 1..3) {
            val folderName = "Folder $i"
            val folder = FolderModel(folderName)
            folderList.add(folder)
        }
        println(folderList.size)
    }
}