package com.moscow.tudee.presentation.screen.task

import com.moscow.tudee.domain.entity.Task
import kotlinx.datetime.LocalDate

interface TaskScreenInteractionListener {
    fun selectDate(date: LocalDate)

    fun selectStatus(status: Task.Status)

    fun deleteTask(task: Task)

    fun previousMonth()

    fun nextMonth()

    fun updateMonthFromPicker(epochMillis: Long?)

    fun showDatePicker()

    fun dismissDatePicker()

}