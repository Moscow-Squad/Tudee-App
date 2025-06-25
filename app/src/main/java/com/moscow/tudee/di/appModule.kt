package com.moscow.tudee.di

import org.koin.dsl.module

val appModule = module {
    includes(
        dataModule, presentationModule
    )
}