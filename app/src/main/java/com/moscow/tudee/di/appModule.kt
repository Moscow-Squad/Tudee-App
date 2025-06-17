package com.moscow.tudee.di

import com.moscow.tudee.di.data.databaseModule
import org.koin.dsl.module

val appModule = module {
    includes(
        databaseModule
    )
}