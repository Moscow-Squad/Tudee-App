package com.moscow.tudee.di

import com.moscow.tudee.di.data.dataModule
import org.koin.dsl.module

val appModule = module {
    includes(
        dataModule
    )
}