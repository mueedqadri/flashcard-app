package com.example.flashcards.persistence

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.graphics.Bitmap
import android.graphics.Bitmap.CompressFormat
import android.graphics.BitmapFactory
import android.net.Uri
import com.example.flashcards.models.FlashCardModel
import com.example.flashcards.models.FolderModel
import com.example.flashcards.models.ImageModel
import java.io.ByteArrayOutputStream


class FlashCardDatabaseHandler(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        const val DATABASE_NAME = "Flashcard.db"
        const val DATABASE_VERSION = 3
        const val FLASH_CARD_TABLE_NAME = "FlashCardTable"
        const val FLASH_CARD_KEY_ID = "id"
        const val FLASH_CARD_KEY_QUESTION = "question"
        const val FLASH_CARD_KEY_ANSWER = "answer"
        const val FLASH_CARD_KEY_FOLDER = "folderId"
        const val FLASH_CARD_KEY_IMAGE_BITMAP = "bitmap"
        const val FLASH_CARD_KEY_IMAGE_URI = "uri"
        const val FLASH_CARD_KEY_HAS_IMAGE = "hasImage"
        const val FOLDER_KEY_ID = "id"
        const val FOLDER_KEY_NAME = "name"
        const val FOLDER_TABLE_NAME = "FolderTable"


        private const val SQL_CREATE_FLASHCARD_TABLE =
            ("CREATE TABLE $FLASH_CARD_TABLE_NAME " +
                    "( $FLASH_CARD_KEY_ID VARCHAR(1000) PRIMARY KEY ," +
                    "$FLASH_CARD_KEY_QUESTION TEXT," +
                    "$FLASH_CARD_KEY_ANSWER TEXT," +
                    "$FLASH_CARD_KEY_IMAGE_BITMAP BLOB," +
                    "$FLASH_CARD_KEY_IMAGE_URI TEXT," +
                    "$FLASH_CARD_KEY_HAS_IMAGE INTEGER," +
                    " $FLASH_CARD_KEY_FOLDER INTEGER" +
                    ")")

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
        values.put(FLASH_CARD_KEY_HAS_IMAGE,file.image.hasImage.compareTo(false))
        if(file.image.hasImage && file.image.bitmap != null){
            values.put(FLASH_CARD_KEY_IMAGE_BITMAP,getBitmapAsByteArray(file.image.bitmap!!))
        }else{
            val arr = byteArrayOf(0)
            values.put(FLASH_CARD_KEY_IMAGE_BITMAP, arr)
        }
        if(file.image.hasImage && file.image.uri != null){
            values.put(FLASH_CARD_KEY_IMAGE_URI, file.image.uri.toString())
        }else{
            values.put(FLASH_CARD_KEY_IMAGE_URI, "")
        }

        val success = db.insert(FLASH_CARD_TABLE_NAME, null, values)

        db.close()
        return success
    }

    private fun getBitmapAsByteArray(bitmap: Bitmap): ByteArray {
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(CompressFormat.PNG, 0, outputStream)
        return outputStream.toByteArray()
    }

    fun deleteFlashCard(id: String): Int {
        val db = this.writableDatabase
        return db.delete(FLASH_CARD_TABLE_NAME, "${FLASH_CARD_KEY_ID}='$id'", null)
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
                val image = ImageModel()
                val flashCardId: String = cursor.getString(cursor.getColumnIndex("id"))
                val flashCardQuestion: String= cursor.getString(cursor.getColumnIndex("question"))
                val flashCardAnswer:String = cursor.getString(cursor.getColumnIndex("answer"))
                val folderId: Int= cursor.getInt(cursor.getColumnIndex("folderId"))
                val uri: String = cursor.getString(cursor.getColumnIndex("uri"))
                image.hasImage = cursor.getInt(cursor.getColumnIndex("hasImage")) ==  1
                if(image.hasImage){
                    val imgByte: ByteArray = cursor.getBlob(cursor.getColumnIndex("bitmap"))
                    image.bitmap = BitmapFactory.decodeByteArray(imgByte, 0, imgByte.size)
                }
                if( uri.isNotEmpty()){
                    image.uri  = Uri.parse(uri)
                }
                val flashCard = FlashCardModel(id = flashCardId, question = flashCardQuestion, answer = flashCardAnswer, folderId= folderId, image = image)
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

    fun deleteFolder(id: Int): Boolean {
        val db = this.writableDatabase
        return db.delete(FOLDER_TABLE_NAME, "${FOLDER_KEY_ID}=$id", null) > 0
                && db.delete(FLASH_CARD_TABLE_NAME, "$FOLDER_KEY_ID=$id", null) > 0
    }
}