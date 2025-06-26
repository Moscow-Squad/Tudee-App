package com.moscow.tudee.di

import androidx.room.Room
import com.moscow.tudee.data.local.DatabaseCallback
import com.moscow.tudee.data.local.dao.CategoryDao
import com.moscow.tudee.data.local.dao.TaskDao
import com.moscow.tudee.data.local.database.TudeeDatabase
import com.moscow.tudee.data.service.CategoryServicesImpl
import com.moscow.tudee.data.service.LocalServicesImpl
import com.moscow.tudee.data.service.TasksServicesImpl
import com.moscow.tudee.domain.service.CategoryServices
import com.moscow.tudee.domain.service.LocalService
import com.moscow.tudee.domain.service.TasksServices
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

private const val TUDEE_DATABASE = "tudee_database"

val databaseModule = module {

    single {
        Room.databaseBuilder(
            androidContext(),
            TudeeDatabase::class.java,
            TUDEE_DATABASE
        )
            .fallbackToDestructiveMigration(false)
            .addCallback(DatabaseCallback())
            .build()
    }

    single<CategoryDao> { get<TudeeDatabase>().categoryDao() }
    single<TaskDao> { get<TudeeDatabase>().taskDao() }
}
