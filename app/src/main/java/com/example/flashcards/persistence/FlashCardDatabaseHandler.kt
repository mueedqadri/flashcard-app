package com.example.flashcards.persistence

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.flashcards.models.FlashCardModel

class FlashCardDatabaseHandler(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        const val DATABASE_NAME = "Flashcard.db"
        const val DATABASE_VERSION = 1
        const val TABLE_NAME = "FlashCardTable"
        const val KEY_ID = "id"
        const val KEY_QUESTION = "question"
        const val KEY_ANSWER = "answer"


        private val SQL_CREATE_ENTRIES =
            ("CREATE TABLE $TABLE_NAME ( $KEY_ID VARCHAR(1000) PRIMARY KEY ,$KEY_QUESTION TEXT,$KEY_ANSWER TEXT)")

        private val SQL_DELETE_ENTRIES = ("DROP TABLE IF EXISTS" + TABLE_NAME)
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_ENTRIES)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    fun addFlashCard(file: FlashCardModel): Long {
        val db = this.writableDatabase

        val values = ContentValues()
        values.put(KEY_ID, file.id)
        values.put(KEY_QUESTION, file.question)
        values.put(KEY_ANSWER,file.answer)

        val success = db.insert(TABLE_NAME, null, values)

        db.close()
        return success
    }

    @SuppressLint("Range")
    fun viewFlashCards(): List<FlashCardModel> {
        val db = this.writableDatabase
        var cursor: Cursor? = null

        val flashCards = ArrayList<FlashCardModel>()
        val query = "SELECT * FROM " + TABLE_NAME
        try {
            cursor = db.rawQuery(query, null)
        } catch(e: SQLException) {
            db.execSQL(query)
            return ArrayList()
        }

        var flashCardId: String
        var flashCardQuestion: String
        var flashCardAnswer:String

        if (cursor.moveToFirst()) {
            do {
                flashCardId = cursor.getString(cursor.getColumnIndex("id"))
                flashCardQuestion = cursor.getString(cursor.getColumnIndex("question"))
                flashCardAnswer = cursor.getString(cursor.getColumnIndex("answer"))
                val flashCard = FlashCardModel(id = flashCardId, question = flashCardQuestion, answer = flashCardAnswer)
                flashCards.add(flashCard)
            } while (cursor.moveToNext())
        }

        return flashCards
    }
}