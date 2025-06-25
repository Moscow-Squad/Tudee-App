package com.moscow.tudee.di

import com.moscow.tudee.presentation.screen.category.categoriesScreen.CategoryViewModel
import com.moscow.tudee.presentation.screen.category.categoryTasksScreen.CategoryTasksViewModel
import com.moscow.tudee.presentation.screen.home.HomeViewModel
import com.moscow.tudee.presentation.screen.task.AddTaskBottomSheetViewModel
import com.moscow.tudee.presentation.screen.task.TaskViewModel
import com.moscow.tudee.presentation.screen.splash.viewmodel.SplashViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val presentationModule = module {
    viewModelOf(::SplashViewModel)
    viewModelOf(::TaskViewModel)
    viewModelOf(::HomeViewModel)
    viewModelOf(::CategoryViewModel)
    viewModelOf(::CategoryTasksViewModel)
    viewModelOf(::AddTaskBottomSheetViewModel)
    viewModel { (categoryId: Long) ->
        CategoryTasksViewModel(categoryId, get(), get())
    }
}

