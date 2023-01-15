package com.uxapp.oktava.viewmodels

import androidx.lifecycle.ViewModel
import com.uxapp.oktava.storage.repositories.CalendarTimesRepository
import com.uxapp.oktava.storage.repositories.ScheduleTimesRepository
import com.uxapp.oktava.storage.repositories.SessionsCompletedRepository

class CalendarViewModel(
    private val sessionsRep: SessionsCompletedRepository,
    private val calendarTimesRep: CalendarTimesRepository,
    private val scheduleTimesRep: ScheduleTimesRepository
): ViewModel() {

    // fun onAddToDoItem(item: ToDoItem) {
    //     rep.addNewItem(item)
    // }
//
    // fun onDeleteToDoItem(item: ToDoItem) {
    //     rep.deleteItem(item)
    // }
//
    // fun onChangeToDoItem(newItem: ToDoItem) {
    //     rep.changeItem(newItem)
    // }
}