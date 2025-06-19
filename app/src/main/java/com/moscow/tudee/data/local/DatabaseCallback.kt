package com.moscow.tudee.data.local

import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

class DatabaseCallback(
    private val predefinedTitles: List<String>
) : RoomDatabase.Callback() {

    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        db.beginTransaction()
        try {
            predefinedTitles.forEach { title ->
                db.execSQL(
                    """
          INSERT INTO categories_table
            (title, iconUri, isPredefined, countOfTasks)
          VALUES (?, NULL, 1, 0)
          """.trimIndent(),
                    arrayOf(title)
                )
            }
            db.setTransactionSuccessful()
        } finally {
            db.endTransaction()
        }
    }
}