package com.moscow.tudee.di

import com.moscow.tudee.data.service.CategoryServicesImpl
import com.moscow.tudee.data.service.LocalServicesImpl
import com.moscow.tudee.data.service.TasksServicesImpl
import com.moscow.tudee.domain.service.CategoryServices
import com.moscow.tudee.domain.service.LocalService
import com.moscow.tudee.domain.service.TasksServices
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val serviceModule = module {
    single<TasksServices> { TasksServicesImpl(get(), get()) }
    single<CategoryServices> { CategoryServicesImpl(get()) }
    single<LocalService> { LocalServicesImpl(androidApplication()) }
}