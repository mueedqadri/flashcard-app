package com.example.flashcards.persistence

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.flashcards.models.FlashCardModel
import com.example.flashcards.models.FolderModel

class FlashCardDatabaseHandler(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        const val DATABASE_NAME = "Flashcard.db"
        const val DATABASE_VERSION = 2
        const val FLASH_CARD_TABLE_NAME = "FlashCardTable"
        const val FLASH_CARD_KEY_ID = "id"
        const val FLASH_CARD_KEY_QUESTION = "question"
        const val FLASH_CARD_KEY_ANSWER = "answer"
        const val FLASH_CARD_KEY_FOLDER = "folderId"
        const val FOLDER_KEY_ID = "id"
        const val FOLDER_KEY_NAME = "name"
        const val FOLDER_TABLE_NAME = "FolderTable"


        private const val SQL_CREATE_FLASHCARD_TABLE =
            ("CREATE TABLE $FLASH_CARD_TABLE_NAME ( $FLASH_CARD_KEY_ID VARCHAR(1000) PRIMARY KEY ,$FLASH_CARD_KEY_QUESTION TEXT,$FLASH_CARD_KEY_ANSWER TEXT, $FLASH_CARD_KEY_FOLDER INTEGER)")

        private const val SQL_DELETE_FLASHCARD = ("DROP TABLE IF EXISTS $FLASH_CARD_TABLE_NAME")
        private const val SQL_DELETE_FOLDERS = ("DROP TABLE IF EXISTS $FOLDER_TABLE_NAME")

        private const val SQL_CREATE_FOLDER_TABLE =
            ("CREATE TABLE $FOLDER_TABLE_NAME ( $FOLDER_KEY_ID INTEGER PRIMARY KEY AUTOINCREMENT,$FOLDER_KEY_NAME TEXT)")

    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_FLASHCARD_TABLE)
        db?.execSQL(SQL_CREATE_FOLDER_TABLE)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL(SQL_DELETE_FLASHCARD)
        db.execSQL(SQL_DELETE_FOLDERS)
        onCreate(db)
    }

    fun addFlashCard(file: FlashCardModel): Long {
        val db = this.writableDatabase

        val values = ContentValues()
        values.put(FLASH_CARD_KEY_ID, file.id)
        values.put(FLASH_CARD_KEY_QUESTION, file.question)
        values.put(FLASH_CARD_KEY_ANSWER,file.answer)
        values.put(FLASH_CARD_KEY_FOLDER,file.folderId)

        val success = db.insert(FLASH_CARD_TABLE_NAME, null, values)

        db.close()
        return success
    }

    fun deleteFlashCard(id: String): Int {
        val db = this.writableDatabase
        return db.delete(FLASH_CARD_TABLE_NAME, "${FLASH_CARD_KEY_ID}=$id", null)
    }

    @SuppressLint("Range")
    fun viewFlashCards(folderId: Int): List<FlashCardModel> {
        val db = this.writableDatabase
        var cursor: Cursor? = null

        val flashCards = ArrayList<FlashCardModel>()
        val query = "SELECT * FROM $FLASH_CARD_TABLE_NAME WHERE $FLASH_CARD_KEY_FOLDER=$folderId"
        try {
            cursor = db.rawQuery(query, null)
        } catch(e: SQLException) {
            db.execSQL(query)
            return ArrayList()
        }
        if (cursor.moveToFirst()) {
            do {
                val flashCardId: String = cursor.getString(cursor.getColumnIndex("id"))
                val flashCardQuestion: String= cursor.getString(cursor.getColumnIndex("question"))
                val flashCardAnswer:String = cursor.getString(cursor.getColumnIndex("answer"))
                val folderId = cursor.getInt(cursor.getColumnIndex("folderId"))
                val flashCard = FlashCardModel(id = flashCardId, question = flashCardQuestion, answer = flashCardAnswer, folderId= folderId)
                flashCards.add(flashCard)
            } while (cursor.moveToNext())
        }

        return flashCards
    }

    fun addFolder(file: FolderModel): Long {
        val db = this.writableDatabase

        val values = ContentValues()
        values.put(FOLDER_KEY_NAME, file.name)

        val success = db.insert(FOLDER_TABLE_NAME, null, values)

        db.close()
        return success
    }

    @SuppressLint("Range")
    fun viewFolders(): List<FolderModel> {
        val db = this.writableDatabase
        var cursor: Cursor? = null

        val folders = ArrayList<FolderModel>()
        val query = "SELECT * FROM $FOLDER_TABLE_NAME"
        try {
            cursor = db.rawQuery(query, null)
        } catch(e: SQLException) {
            db.execSQL(query)
            return ArrayList()
        }



        if (cursor.moveToFirst()) {
            do {
                val folderId: Int = cursor.getInt(cursor.getColumnIndex("id"))
                val folderName: String = cursor.getString(cursor.getColumnIndex("name"))
                val folder = FolderModel(id = folderId, name = folderName)
                folders.add(folder)
            } while (cursor.moveToNext())
        }

        return folders
    }

    fun deleteFolder(id: String): Int {
        val db = this.writableDatabase
        return db.delete(FOLDER_TABLE_NAME, "${FOLDER_KEY_ID}=$id", null)
    }
}