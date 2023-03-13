package com.uxapp.oktava.viewmodels.interfaces

import kotlinx.coroutines.flow.Flow
import java.util.*

interface CalendarDataProvider {
    fun getPlannedDaysForMonthFlow(month: Int, year: Int): Flow<List<Calendar>>

    fun getPassedDaysForMonthFlow(month: Int, year: Int): Flow<List<Calendar>>
}