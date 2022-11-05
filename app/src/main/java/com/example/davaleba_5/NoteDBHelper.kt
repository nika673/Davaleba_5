package com.example.davaleba_5

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class NoteDBHelper(context: Context) :
    SQLiteOpenHelper(context, DBconfig.DATABASE_NAME, null, DBconfig.VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(DROP_TABLE)
        onCreate(db)
    }

    companion object {
        private const val CREATE_TABLE =
            "CREATE TABLE ${NoteDB.TABLE_NAME}(" + "${NoteDB.NOTES_COLUMNS.ID} INTEGER PRIMARY KEY," + "${NoteDB.NOTES_COLUMNS.NOTE_TEXT} TEXT)"

        private const val DROP_TABLE = "DROP TABLE IF EXISTS ${NoteDB.TABLE_NAME}"

    }

    fun insert(noteText: String) {
        val cv = ContentValues().apply {
            this.put(NoteDB.NOTES_COLUMNS.NOTE_TEXT, noteText)
        }
        writableDatabase.insert(NoteDB.TABLE_NAME, null, cv)

    }

}