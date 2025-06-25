package com.moscow.tudee.data.local

import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime

class DatabaseCallback(
    private val predefinedTitles: List<String>
) : RoomDatabase.Callback() {

    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)

        db.beginTransaction()
        try {
            predefinedTitles.forEach { title ->
                db.execSQL("""
          INSERT INTO categories_table
            (title, iconUri, isPredefined, countOfTasks)
          VALUES (?, NULL, 1, 0)
        """.trimIndent(), arrayOf(title))
            }

            val cursor = db.query(
                "SELECT id, title FROM categories_table WHERE title IN (?, ?, ?)",
                predefinedTitles.take(3).toTypedArray()
            )
            val entries = mutableListOf<Pair<Long,String>>()
            while (cursor.moveToNext()) {
                val id    = cursor.getLong(cursor.getColumnIndexOrThrow("id"))
                val title = cursor.getString(cursor.getColumnIndexOrThrow("title"))
                entries += id to title
            }
            cursor.close()

            val statuses = listOf("TODO", "IN_PROGRESS", "DONE")
            val today    = LocalDateTime.parse("2025-06-20T00:00:00")  // e.g. "2025-06-20"
            entries.forEach { (catId, catTitle) ->
                statuses.forEach { status ->
                    repeat(3) { idx ->
                        db.execSQL("""
              INSERT INTO tasks_table
                (title, description, priority, categoryId, status, date)
              VALUES (?, ?, ?, ?, ?, ?)
            """.trimIndent(), arrayOf(
                            "$catTitle $status task #${idx+1}",
                            "This is dummy #${idx+1} in $status for $catTitle",
                            "MEDIUM",
                            catId,
                            status,
                            today
                        ))
                    }
                }
            }

            db.setTransactionSuccessful()
        } finally {
            db.endTransaction()
        }
    }
}