package com.example.flashcards

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class FileDatabaseHandler(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        val DATABASE_NAME = "Flashcard.db"
        val DATABASE_VERSION = 1
        val TABLE_NAME = "FileTable"
        val KEY_ID = "id"
        val KEY_NAME = "name"

        private val SQL_CREATE_ENTRIES =
            ("CREATE TABLE" + TABLE_NAME + "(" + KEY_ID + " INTEGER PRIMARY KEY" + KEY_NAME + " TEXT" + ")")

        private val SQL_DELETE_ENTRIES = ("DROP TABLE IF EXISTS" + TABLE_NAME)
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    fun addFile(file: FileModelClass): Long {
        val db = this.writableDatabase

        val values = ContentValues()
        values.put(KEY_ID, file.fileId)
        values.put(KEY_NAME, file.fileName)

        val success = db.insert(TABLE_NAME, null, values)

        db.close()
        return success
    }

    @SuppressLint("Range")
    fun viewFiles(): List<FileModelClass> {
        val db = this.writableDatabase
        var cursor: Cursor? = null

        val files = ArrayList<FileModelClass>()
        val query = "SELECT * FROM " + TABLE_NAME
        try {
            cursor = db.rawQuery(query, null)
        } catch(e: SQLException) {
            db.execSQL(query)
            return ArrayList()
        }

        var fileId: Int
        var fileName: String

        if (cursor.moveToFirst()) {
            while (cursor.isAfterLast == false) {
                fileId = cursor.getInt(cursor.getColumnIndex("id"))
                fileName = cursor.getString(cursor.getColumnIndex("name"))

                val file = FileModelClass(fileId = fileId, fileName = fileName)
                files.add(file)
            }
        }

        return files
    }
}