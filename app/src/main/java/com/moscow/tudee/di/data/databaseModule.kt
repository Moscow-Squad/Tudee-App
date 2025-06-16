package com.moscow.tudee.di.data

import androidx.room.Room
import com.moscow.tudee.data.local.dao.CategoryDao
import com.moscow.tudee.data.local.dao.TaskDao
import com.moscow.tudee.data.local.database.TudeeDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

private const val TUDEE_DATABASE = "tudee_database"

val databaseModule = module {
    single<TudeeDatabase> {
        Room.databaseBuilder(androidContext(), TudeeDatabase::class.java, TUDEE_DATABASE).build()
    }
    single<CategoryDao> {
        Room.databaseBuilder(androidContext(), TudeeDatabase::class.java, TUDEE_DATABASE).build()
            .categoryDao()
    }
    single<TaskDao> {
        Room.databaseBuilder(androidContext(), TudeeDatabase::class.java, TUDEE_DATABASE).build()
            .taskDao()
    }
}
